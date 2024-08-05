package com.tinqinacademy.hotel.core.services.implementations;

import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InputDataException;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateRoomOperationProcessor implements UpdateRoomOperation{
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final ErrorHandler errorHandler;
    @Override
    public Either<ErrorWrapper, UpdateRoomOutput> process(UpdateRoomInput input) {
        log.info("Start updateRoom input: {}", input);

       return Try.of( ()->{
                   checkIfRoomExists(input);
                   checkForBathroomtype(input);
                   List<String> bedStrings = Arrays.stream(Bed.values()).map(Bed::toString).toList();
        List<String> inputBedEntities = input.getBeds();
        if(Collections.disjoint(bedStrings,inputBedEntities)){
            throw new InputDataException("Given list does not contain any valid bed");
        }
        inputBedEntities.retainAll(bedStrings);
                   List<BedEntity> entities = buildBedEntities(inputBedEntities);
                   Room updateEntity = buildUpdatedEntity(input, entities);
                   log.info("Roomentity,{}", updateEntity);
                     roomRepository.flush();
                   UpdateRoomOutput output = buildOutput(updateEntity);
                   log.info("End updateRoom output: {}", output);
        return output;})
               .toEither()
               .mapLeft(errorHandler::handleError);

    }

    private List<BedEntity> buildBedEntities(List<String> inputBedEntities) {
        return inputBedEntities.stream()
                .map(Bed::getByCode)
                .filter(bedType->!bedType.equals(Bed.UNKNOWN))
                .map(bed -> bedRepository.findEntityByType(bed.name()))
                .toList();
    }

    private static UpdateRoomOutput buildOutput(Room updateEntity) {
        return UpdateRoomOutput.builder()
                .id(updateEntity.getId())
                .build();
    }

    private Room buildUpdatedEntity(UpdateRoomInput input, List<BedEntity> entities) {
        Room updateEntity = roomRepository.getReferenceById(input.getId());
        updateEntity.setBathroomPrototype(BathRoomPrototype.getByCode(input.getBathroomType()));
        updateEntity.setFloor(input.getFloor());
        updateEntity.setPrice(input.getPrice());
        updateEntity.setRoomNumber(input.getRoomNumber());
        updateEntity.setBeds(entities);
        return updateEntity;
    }

    private void checkIfRoomExists(UpdateRoomInput input) {
        roomRepository.findRoomById(input.getId())
             .orElseThrow(()->new InvalidRoomByIdExceptions("Room not found"));
    }

    private  void checkForBathroomtype(UpdateRoomInput input) {
        BathRoomPrototype bathRoom = BathRoomPrototype.getByCode(input.getBathroomType());
        if (bathRoom.equals(BathRoomPrototype.UNKNOWN)) {
            throw new InputDataException("Bathroom is unknown");
        }
    }
}
