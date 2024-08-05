package com.tinqinacademy.hotel.api.models.exceptions.customException;

public class InputDataException extends RuntimeException{
    public InputDataException(String message) {
        super(message);
    }
}
