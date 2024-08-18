package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BaseOperationProcessor {
    private final Validator validator;
    protected final ErrorHandler errorHandler;


    public BaseOperationProcessor(Validator validator, ErrorHandler errorHandler) {
        this.validator = validator;
        this.errorHandler = errorHandler;
    }

    public Either<ErrorWrapper, ? extends OperationInput> validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> constraintViolations = validator.validate(input);

        if (constraintViolations.isEmpty()) {
            return Either.right(input);
        }

        ErrorWrapper errors = errorHandler.handleViolations(constraintViolations, HttpStatus.BAD_REQUEST);
        return Either.left(errors);
    }
}
