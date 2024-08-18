package com.tinqinacademy.hotel.core.services.implementations.hotelImpl;

import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UnbookRoomOperationProcessor extends BaseOperationProcessor implements UnbookRoomOperation {
    private final ReservationRepository reservationRepository;

    public UnbookRoomOperationProcessor(Validator validator, ErrorHandler errorHandler, ReservationRepository reservationRepository) {
        super(validator, errorHandler);
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Either<ErrorWrapper, UnbookRoomOutput> process(UnbookRoomInput input) {
        log.info("Start deleteRoom input: {}", input);
        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                    reservationRepository.deleteById(input.getBookingRoomId());
                    UnbookRoomOutput output = outputBuilder();
                    log.info("End deleteRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(errorHandler::handleError));
    }

    private UnbookRoomOutput outputBuilder() {
        return UnbookRoomOutput.builder()
                .message("Deleted successfully")
                .build();
    }
}
