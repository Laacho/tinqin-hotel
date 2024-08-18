package com.tinqinacademy.hotel.core.services.implementations.systemImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.exceptions.errorHandler.ErrorHandler;
import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.core.services.implementations.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PartialUpdateRoomOperationProcessor extends BaseOperationProcessor implements PartialUpdateRoomOperation {
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;
    private final BedRepository bedRepository;

    public PartialUpdateRoomOperationProcessor(Validator validator, ErrorHandler errorHandler, RoomRepository roomRepository, ObjectMapper objectMapper, ConversionService conversionService, BedRepository bedRepository) {
        super(validator, errorHandler);
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
        this.conversionService = conversionService;
        this.bedRepository = bedRepository;
    }

    @Override
    public Either<ErrorWrapper, PartialUpdateRoomOutput> process(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom input: {}", input);

        return validateInput(input).flatMap(validatedInput -> Try.of(() -> {
                            Room room = roomRepository.findRoomById(input.getId())
                                    .orElseThrow(() -> new InvalidRoomByIdExceptions("Invalid room"));
                            Room converted = conversionService.convert(input, Room.class);
                            if (input.getBeds() != null && !input.getBeds().isEmpty()) {
                                List<BedEntity> result = new ArrayList<>();
                                List<String> beds = input.getBeds();
                                for (String bed : beds) {
                                    result.add(bedRepository.findBedEntityByBedType(BedSize.getByCode(bed)));
                                }
                                converted.setBeds(result);
                            }
//                            if(input.getBathroomType()==null){
//                                BathRoomPrototype bathroomPrototype = room.getBathroomPrototype();
//                                converted.setBathroomPrototype(bathroomPrototype);
//                            }
                            JsonNode roomNode = objectMapper.valueToTree(room);
                            JsonNode inputNode = objectMapper.valueToTree(converted);

                            try {
                                JsonMergePatch patch = JsonMergePatch.fromJson(inputNode);
                                JsonNode patchedNode = patch.apply(roomNode);

                                Room patchedRoom = objectMapper.treeToValue(patchedNode, Room.class);
                                log.info("Patched room: {}", patchedRoom);

                                roomRepository.save(patchedRoom);
                                PartialUpdateRoomOutput output = outputBuilder(patchedRoom);
                                log.info("End partialUpdateRoom output: {}", output);
                                return output;
                            } catch (JsonPatchException | JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }

                        }
                ).toEither()
                .mapLeft(errorHandler::handleError));
    }

    private List<BedEntity> convert(PartialUpdateRoomInput input) {
        List<String> beds = input.getBeds();
        List<BedEntity> result = new ArrayList<>();
        for (String bed : beds) {
            BedSize bedSize = BedSize.getByCode(bed);
            BedEntity bedEntity = BedEntity.builder()
                    .bedType(bedSize)
                    .capacity(bedSize.getCount())
                    .build();
            result.add(bedEntity);
        }
        return result;
    }

    private PartialUpdateRoomOutput outputBuilder(Room newRoom) {
        return PartialUpdateRoomOutput.builder()
                .id(newRoom.getId())
                .build();
    }
}
