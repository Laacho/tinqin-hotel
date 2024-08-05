package com.tinqinacademy.hotel.api.models.exceptions.customException;

public class InvalidRoomByRoomNumberException extends RuntimeException{
    public InvalidRoomByRoomNumberException(String message) {
        super(message);
    }
}
