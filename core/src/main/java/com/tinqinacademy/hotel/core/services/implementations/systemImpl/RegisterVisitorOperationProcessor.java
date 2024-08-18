package com.tinqinacademy.hotel.core.services.implementations.systemImpl;

import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByRoomNumberException;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForEachVisitor;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.core.services.converters.GuestToDataConverter;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.entities.Reservation;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements RegisterVisitorOperation {
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final GuestToDataConverter dataConverter;

    public RegisterVisitorOperationProcessor(Validator validator, ErrorHandler errorHandler, GuestRepository guestRepository, ReservationRepository reservationRepository, RoomRepository roomRepository, GuestToDataConverter dataConverter) {
        super(validator, errorHandler);
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.dataConverter = dataConverter;
    }

    @Override
    public Either<ErrorWrapper, RegisterVisitorOutput> process(RegisterVisitorInput input) {
        log.info("Start registerRoom input: {}", input);
        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                    List<Guest> guests = getGuests(input);
                    List<Guest> guestsFromDatabase = guestRepository.saveAll(guests);
                    Room roomFound = roomRepository.findRoomByRoomNumber(input.getRoomNumber())
                            .orElseThrow(() -> new InvalidRoomByRoomNumberException("Invalid room number"));

                    Reservation foundReservation = reservationRepository.findReservationByRoomId(roomFound.getId())
                            .orElseThrow(() -> new InvalidRoomByIdExceptions("Invalid room"));
                    foundReservation.setGuests(guestsFromDatabase);
                    reservationRepository.save(foundReservation);
                    RegisterVisitorOutput output = outputBuilder();
                    log.info("End registerRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(errorHandler::handleError));
    }

    private List<Guest> getGuests(RegisterVisitorInput input) {
        List<Guest> guests = new ArrayList<>();

        for (DataForEachVisitor dataForVisitor : input.getDataForVisitors()) {
            Guest guest = dataConverter.convert(dataForVisitor);
            guests.add(guest);
        }
        return guests;
    }

    private RegisterVisitorOutput outputBuilder() {
        return RegisterVisitorOutput.builder()
                .message("Successfully register visitors")
                .build();
    }
}
