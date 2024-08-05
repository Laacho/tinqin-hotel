package com.tinqinacademy.hotel.api.models.exceptions.errorHandler;

import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;

public interface ErrorHandler {

    ErrorWrapper handleError(Throwable t);
}
