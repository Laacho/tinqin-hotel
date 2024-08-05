package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidBedTypeException;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
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
public class CreateRoomOperationProcessor implements CreateRoomOperation {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final ErrorHandler errorHandler;

    @Override
    public Either<ErrorWrapper, CreateRoomOutput> process(CreateRoomInput input) {
        log.info("Start createRoom input: {}", input);
        return Try.of(() -> {
                    List<BedEntity> result = convertFromStringToBedEntity(input);
                    Room room = roomBuilder(input, result);
                    saveRoom(room);

                    CreateRoomOutput output = outputBuilder(room);
                    log.info("End createRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(errorHandler::handleError);
    }

    private  CreateRoomOutput outputBuilder(Room room) {
        return CreateRoomOutput.builder()
                .id(room.getId())
                .build();
    }

    private void saveRoom(Room room) {
        try {
            roomRepository.save(room);
        } catch (IllegalArgumentException e) {
            errorHandler.handleError(e);
        }
    }

    private Room roomBuilder(CreateRoomInput input, List<BedEntity> result) {
        return Room.builder()
                .roomNumber(input.getRoomNumber())
                .bathroomPrototype(BathRoomPrototype.getByCode(input.getBathRoomType()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .beds(result)
                .build();
    }

    private List<BedEntity> convertFromStringToBedEntity(CreateRoomInput input) {
        List<String> beds = input.getBeds();
        List<BedEntity> result = new ArrayList<>();
        for (String bed : beds) {
            BedEntity bedEntity = bedRepository.findBedByBedType(BedSize.getByCode(bed))
                    .orElseThrow(() -> new InvalidBedTypeException("Cannot find bed"));
            result.add(bedEntity);
        }
        return result;
    }
}
