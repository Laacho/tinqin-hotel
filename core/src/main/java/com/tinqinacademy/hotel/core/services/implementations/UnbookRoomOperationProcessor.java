package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.repository.interfaces.ReservationRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnbookRoomOperationProcessor implements UnbookRoomOperation {
    private final ReservationRepository reservationRepository;
    private final ErrorHandler errorHandler;
    @Override
    public Either<ErrorWrapper, UnbookRoomOutput> process(UnbookRoomInput input) {
        log.info("Start deleteRoom input: {}", input);
     return Try.of( ()->{
             reservationRepository.deleteById(input.getBookingRoomId());
                 UnbookRoomOutput output = outputBuilder();
                 log.info("End deleteRoom output: {}", output);
        return output;
     })
             .toEither()
             .mapLeft(errorHandler::handleError);
    }

    private  UnbookRoomOutput outputBuilder() {
        return UnbookRoomOutput.builder()
                .message("Deleted successfully")
                .build();
    }
}
