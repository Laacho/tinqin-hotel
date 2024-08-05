package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForEachVisitor;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterVisitorOperationProcessor implements RegisterVisitorOperation {
    private final GuestRepository guestRepository;
    private final ErrorHandler errorHandler;
    @Override
    public Either<ErrorWrapper, RegisterVisitorOutput> process(RegisterVisitorInput input) {
        log.info("Start registerRoom input: {}", input);
      return Try.of(()->{
                  List<Guest> guests = getGuests(input);
                  guestRepository.saveAll(guests);
        RegisterVisitorOutput output = outputBuilder();
        log.info("End registerRoom output: {}", output);
        return output;
      })
              .toEither()
              .mapLeft(errorHandler::handleError);
    }

    private  List<Guest> getGuests(RegisterVisitorInput input) {
        List<Guest> guests = new ArrayList<>();

        for (DataForEachVisitor dataForVisitor : input.getDataForVisitors()) {
            Guest guest = Guest.builder()
                    .firstName(dataForVisitor.getFirstName())
                    .lastName(dataForVisitor.getLastName())
                    .birthday(dataForVisitor.getBirthdate())
                    .idCardAuthority(dataForVisitor.getIdCardIssueAuthority())
                    .idCardNumber(dataForVisitor.getIdCardNumber())
                    .idCardValidity(dataForVisitor.getIdCardValidity())
                    .idCardIssueDate(dataForVisitor.getIdCardIssueDate())
                    .build();
            guests.add(guest);
        }
        return guests;
    }

    private  RegisterVisitorOutput outputBuilder() {
        return RegisterVisitorOutput.builder()
                .message("Successfully register visitors")
                .build();
    }
}
