package com.tinqinacademy.hotel.core.services.implementations.systemImpl;

import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidBedTypeException;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOutput;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CreateRoomOperationProcessor extends BaseOperationProcessor implements CreateRoomOperation {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    public CreateRoomOperationProcessor(Validator validator, ErrorHandler errorHandler, BedRepository bedRepository, RoomRepository roomRepository) {
        super(validator, errorHandler);
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorWrapper, CreateRoomOutput> process(CreateRoomInput input) {
        log.info("Start createRoom input: {}", input);
        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                    List<BedEntity> result = convertFromStringToBedEntity(input);
                    Room room = roomBuilder(input, result);
                    saveRoom(room);

                    CreateRoomOutput output = outputBuilder(room);
                    log.info("End createRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(errorHandler::handleError));
    }

    private CreateRoomOutput outputBuilder(Room room) {
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
