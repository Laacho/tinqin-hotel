package com.tinqinacademy.hotel.core.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InputDataException;
import com.tinqinacademy.hotel.api.models.exceptions.customException.InvalidRoomByIdExceptions;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportInput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemRepostOutput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOutput;
import com.tinqinacademy.hotel.core.services.contracts.SystemService;
import com.tinqinacademy.hotel.core.services.converters.BedEnumToBedEntity;
import com.tinqinacademy.hotel.core.services.converters.PartialUpdateRoomInputToRoomEntity;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.interfaces.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class SystemServiceImpl implements SystemService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BedRepository bedRepository;
    private final BedEnumToBedEntity bedEnumToBedEntity;
    private final ObjectMapper objectMapper;
    private final PartialUpdateRoomInputToRoomEntity partialUpdateRoomInputToRoomEntity;
    private final EntityManager entityManager;


    @Override
    public RegisterVisitorOutput registerVisitor(List<RegisterVisitorInput> input) {
//        log.info("Start registerRoom input: {}", input);
//
//        List<Guest> guests = new ArrayList<>();
//        for (RegisterVisitorInput roomInput : input) {
//            Guest guest = Guest.builder()
//                    .firstName(roomInput.getFirstName())
//                    .lastName(roomInput.getLastName())
//                    .birthday(roomInput.getBirthdate())
//                    .idCardAuthority(roomInput.getIdCardIssueAuthority())
//                    .idCardNumber(roomInput.getIdCardNumber())
//                    .idCardValidity(roomInput.getIdCardValidity())
//                    .idCardIssueDate(roomInput.getIdCardIssueDate())
//                    .build();
//            guests.add(guest);
//        }
//        guestRepository.saveAll(guests);
//        RegisterVisitorOutput output = RegisterVisitorOutput.builder()
//                .message("Success")
//                .build();
//        log.info("End registerRoom output: {}", output);
//        return output;
        return null;
    }

    @Override
    public SystemRepostOutput reportRooms(SystemReportInput input) {
        log.info("Start reportRooms input: {}", input);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        SystemRepostOutput output = SystemRepostOutput.builder()
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .idCardNumber(input.getIdCardNumber())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .build();
        log.info("End reportRooms output: {}", output);
        return output;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {

        log.info("Start createRoom input: {}", input);
        List<String> beds = input.getBeds();
        List<BedEntity> result=new ArrayList<>();
        for (String bed : beds) {
            BedEntity tempBed = bedRepository.findBedByBedType(BedSize.getByCode(bed)).get();
            result.add(tempBed);
        }
        Room room = Room.builder()
                .roomNumber(input.getRoomNumber())
                .bathroomPrototype(BathRoomPrototype.getByCode(input.getBathRoomType()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .beds(result)
                .build();
        roomRepository.save(room);
        CreateRoomOutput output = CreateRoomOutput.builder()
                .id(room.getId())
                .build();
        log.info("End createRoom output: {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom input: {}", input);

        roomRepository.findRoomById(input.getId())
                .orElseThrow(()->new InvalidRoomByIdExceptions("Room not found"));
        BathRoomPrototype bathRoom = BathRoomPrototype.getByCode(input.getBathroomType());
        if (bathRoom.equals(BathRoomPrototype.UNKNOWN)) {
            throw new InputDataException("Bathroom is unknown");
        }
        List<String> bedStrings = Arrays.stream(Bed.values()).map(Bed::toString).toList();
        List<String> inputBedEntities = input.getBeds();
        if(Collections.disjoint(bedStrings,inputBedEntities)){
            throw new InputDataException("Given list does not contain any valid bed");
        }
        inputBedEntities.retainAll(bedStrings);
        List<BedEntity> entities=inputBedEntities.stream()
                .map(Bed::getByCode)
                .filter(bedType->!bedType.equals(Bed.UNKNOWN))
                .map(bed -> bedRepository.findEntityByType(bed.name()))
                .toList();
        Room updateEntity = roomRepository.getReferenceById(input.getId());
        updateEntity.setBathroomPrototype(BathRoomPrototype.getByCode(input.getBathroomType()));
        updateEntity.setFloor(input.getFloor());
        updateEntity.setPrice(input.getPrice());
        updateEntity.setRoomNumber(input.getRoomNumber());
        updateEntity.setBeds(entities);
        log.info("Roomentity,{}", updateEntity);
        roomRepository.flush();
        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .id(updateEntity.getId())
                .build();
        log.info("End updateRoom output: {}", output);
        return output;
    }


    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom input: {}", input);

        Optional<Room> room = roomRepository.findRoomById(input.getId());

        JsonNode currentRoom = objectMapper.valueToTree(room);
        Room newRoom = partialUpdateRoomInputToRoomEntity.convert(input);
        JsonNode newRoomNode = objectMapper.valueToTree(newRoom);
        try {
            JsonMergePatch patch = JsonMergePatch.fromJson(newRoomNode);
            newRoom=objectMapper.treeToValue(patch.apply(currentRoom),Room.class);
            roomRepository.save(newRoom);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .id(newRoom.getId())
                .build();
        log.info("End partialUpdateRoom output: {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {

        log.info("Start deleteRoom input: {}", input);
        Optional<Room> result = roomRepository.findRoomById(input.getRoomId());
        if (result.isEmpty()) {
            throw new InvalidRoomByIdExceptions("Room with this id: " + input.getRoomId() + "doesnt exists");
        }
        Room room = result.get();
        roomRepository.delete(room);
        DeleteRoomOutput output = DeleteRoomOutput.builder()
                .message("Deleted successfully the given room!")
                .build();
        log.info("End deleteRoom output: {}", output);
        return output;
    }
}
