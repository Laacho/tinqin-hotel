package com.tinqinacademy.hotel.core.services.implementations.hotelImpl;

import com.tinqinacademy.hotel.api.models.enums.BathRoomType;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIdOperation;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Reservation;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GetRoomByIdOperationProcessor extends BaseOperationProcessor implements GetRoomByIdOperation {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public GetRoomByIdOperationProcessor(Validator validator, ErrorHandler errorHandler, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        super(validator, errorHandler);
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Either<ErrorWrapper, GetRoomByIDOutput> process(GetRoomByIDInput input) {
        log.info("Start getRoomByID input: {}", input);
        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                            Room room = getRoom(input);
                            List<Bed> bedList = new ArrayList<>();
                            for (BedEntity bedEntity : room.getBeds()) {
                                bedList.add(Bed.getByCode(bedEntity.getBedType().getCode()));
                            }
                            List<Reservation> allByRoomId = reservationRepository.findAllByRoomIdOrderByStartDate(input.getRoomID());
                            Map<LocalDate, LocalDate> datesOccupied = getDatesOccupied(allByRoomId);

                            GetRoomByIDOutput output = outputBuilder(room, bedList, datesOccupied);
                            log.info("End getRoomById output: {}", output);
                            return output;
                        }
                )
                .toEither()
                .mapLeft(errorHandler::handleError));
    }

    private GetRoomByIDOutput outputBuilder(Room room, List<Bed> bedList, Map<LocalDate, LocalDate> datesOccupied) {
        return GetRoomByIDOutput.builder()
                .id(room.getId())
                .price(room.getPrice())
                .floor(room.getFloor())
                .bathRoomType(BathRoomType.getByCode(String.valueOf(room.getBathroomPrototype())))
                .bed(bedList)
                .datesOccupied(datesOccupied)
                .build();
    }

    private Map<LocalDate, LocalDate> getDatesOccupied(List<Reservation> allByRoomId) {
        Map<LocalDate, LocalDate> datesOccupied = new LinkedHashMap<>();
        for (Reservation reservation : allByRoomId) {
            LocalDate startDate = reservation.getStartDate();
            LocalDate endDate = reservation.getEndDate();
            datesOccupied.put(startDate, endDate);
        }
        return datesOccupied;
    }

    private Room getRoom(GetRoomByIDInput input) {
        return roomRepository.findRoomById(input.getRoomID())
                .orElseThrow(() -> new InvalidRoomByIdExceptions("Room with this id: " + input.getRoomID() + "doesnt exists!"));

    }
}
