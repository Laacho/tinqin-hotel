package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteRoomOperationProcessor implements DeleteRoomOperation {
    private final RoomRepository roomRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<ErrorWrapper, DeleteRoomOutput> process(DeleteRoomInput input) {

        log.info("Start deleteRoom input: {}", input);
        return Try.of(() -> {
                    Room room = getRoom(input);
                    extracted(room);
                    DeleteRoomOutput output = getDeleteRoomOutput();
                    log.info("End deleteRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(errorHandler::handleError);

    }


    private DeleteRoomOutput getDeleteRoomOutput() {
        return DeleteRoomOutput.builder()
                .message("Deleted successfully the given room!")
                .build();
    }

    private void extracted(Room room) {
        roomRepository.delete(room);
    }

    private Room getRoom(DeleteRoomInput input) {
        return roomRepository
                .findRoomById(input.getRoomId())
                .orElseThrow(() ->
                        new InvalidRoomByIdExceptions("Room with this id: " + input.getRoomId() + "doesnt exists"));
    }
}
