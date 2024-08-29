package com.tinqinacademy.hotel.core.services.implementations.hotelImpl;

import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOutput;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.entities.Reservation;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.entities.User;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class BookRoomOperationProcessor extends BaseOperationProcessor implements BookRoomOperation {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public BookRoomOperationProcessor(Validator validator, ErrorHandler errorHandler, RoomRepository roomRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        super(validator, errorHandler);
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Either<ErrorWrapper, BookRoomOutput> process(BookRoomInput input) {

        log.info("Start bookRoom input: {}", input);
        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                         User user = buildUser(input);
                            userRepository.save(user);
                            Room result = getRoomById(input);
                            BigDecimal finalPrice = calculateFinalPrice(input, result);
                            Reservation reservation = buildReservation(input, finalPrice, result, user);
                            reservationRepository.save(reservation);
                            BookRoomOutput output = buildOutput();
                            log.info("End bookRoom output: {}", output);
                            return output;
                        }
                )
                .toEither()
                .mapLeft(errorHandler::handleError));

    }

    private BookRoomOutput buildOutput() {
        return BookRoomOutput.builder()
                .message("Successfully booked a room!")
                .build();
    }

    private Reservation buildReservation(BookRoomInput input, BigDecimal finalPrice, Room result, User user) {
        return Reservation.builder()
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .price(finalPrice)
                .room(result)
                .user(user)
                .build();
    }

    private BigDecimal calculateFinalPrice(BookRoomInput input, Room result) {
        Long daysAtTheHotel = ChronoUnit.DAYS.between(input.getStartDate(), input.getEndDate());
        return result.getPrice().multiply(BigDecimal.valueOf(daysAtTheHotel));
    }

    private Room getRoomById(BookRoomInput input) {
        return roomRepository.findRoomById(input.getRoomId())
                .orElseThrow(() -> new InvalidRoomByIdExceptions("Room with this ID: " + input.getRoomId() + "doesnt exists!"));
    }

    private User buildUser(BookRoomInput input) {
        return User.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .birthday(input.getBirthdate())
                .email(input.getEmail())
                .build();
    }
}
