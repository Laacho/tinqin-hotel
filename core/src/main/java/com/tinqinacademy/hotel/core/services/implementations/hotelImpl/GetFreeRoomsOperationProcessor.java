package com.tinqinacademy.hotel.core.services.implementations.hotelImpl;

import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsInput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOperation;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOutput;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Reservation;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GetFreeRoomsOperationProcessor extends BaseOperationProcessor implements GetFreeRoomsOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public GetFreeRoomsOperationProcessor(Validator validator, ErrorHandler errorHandler, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        super(validator, errorHandler);
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Either<ErrorWrapper, GetFreeRoomsOutput> process(GetFreeRoomsInput input) {

        log.info("Start getFreeRooms input: {}", input);
        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                            List<Room> roomsByMe = getFinalRooms(input);
                            List<UUID> result = new ArrayList<>();
                            for (Room room : roomsByMe) {
                                if (room.getBathroomPrototype().equals(BathRoomPrototype.getByCode(input.getBathRoomType().toLowerCase()))) {
                                    if (room.getBeds().size() == input.getBeds().size()) {
                                        List<BedSize> bedSizeList = room.getBeds().stream().map(BedEntity::getBedType).toList();
                                        List<String> bedsInput = input.getBeds();
                                        boolean allBedSizesMatch = true;
                                        for (BedSize bedSize : bedSizeList) {
                                            if (!bedsInput.contains(bedSize.getCode())) {
                                                allBedSizesMatch = false;
                                                break;
                                            }
                                        }
                                        if (allBedSizesMatch) {
                                            result.add(room.getId());
                                        }
                                    }
                                }
                            }
                            GetFreeRoomsOutput output = outputBuilder(result);
                            log.info("End getFreeRooms output: {}", output);
                            return output;
                        }
                )
                .toEither()
                .mapLeft(errorHandler::handleError));


    }

    private GetFreeRoomsOutput outputBuilder(List<UUID> result) {
        return GetFreeRoomsOutput.builder()
                .ids(result)
                .build();
    }

    private List<Room> getFinalRooms(GetFreeRoomsInput input) {
        List<Room> roomsByMe = roomRepository.findRoomsByMe();
        List<Reservation> reservationByEndDateAfter = getReservations(input);
        for (Reservation reservation : reservationByEndDateAfter) {
            roomsByMe.add(reservation.getRoom());
        }
        return roomsByMe;
    }

    private List<Reservation> getReservations(GetFreeRoomsInput input) {
        return reservationRepository.findReservationByEndDateAfter(input.getStartDate());
    }
}
