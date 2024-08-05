package com.tinqinacademy.hotel.api.models.base;


import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import io.vavr.control.Either;

public interface OperationProcess<I extends OperationInput, O extends OperationOutput>  {

    Either<ErrorWrapper,O> process(I input);
}
