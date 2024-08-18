package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    public ResponseEntity<?> handleResponse(Either<ErrorWrapper, ? extends OperationOutput> result) {
        if (result.isRight()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getLeft());

    }
}
