package com.tinqinacademy.hotel.rest.controllers.system;

import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportInput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportOperation;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemRepostOutput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOutput;
import com.tinqinacademy.hotel.core.services.paths.HotelURLPaths;
import com.tinqinacademy.hotel.rest.controllers.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class SystemController extends BaseController{
    private final RegisterVisitorOperation registerVisitorOperation;
    private final SystemReportOperation systemReportOperation;
    private final CreateRoomOperation createRoomOperation;
    private final UpdateRoomOperation updateRoomOperation;
    private final DeleteRoomOperation deleteRoomOperation;
    private final PartialUpdateRoomOperation partialUpdateRoomOperation;


    public SystemController(RegisterVisitorOperation registerVisitorOperation, SystemReportOperation systemReportOperation, CreateRoomOperation createRoomOperation, UpdateRoomOperation updateRoomOperation, DeleteRoomOperation deleteRoomOperation, PartialUpdateRoomOperation partialUpdateRoomOperation) {
        this.registerVisitorOperation = registerVisitorOperation;
        this.systemReportOperation = systemReportOperation;
        this.createRoomOperation = createRoomOperation;
        this.updateRoomOperation = updateRoomOperation;
        this.deleteRoomOperation = deleteRoomOperation;
        this.partialUpdateRoomOperation = partialUpdateRoomOperation;
    }


    @PostMapping(HotelURLPaths.POST_REGISTER)
    @Operation(summary = "registers a visitor DONE")
    public ResponseEntity<?> registerVisitor( @RequestBody RegisterVisitorInput input) {
        Either<ErrorWrapper, RegisterVisitorOutput> result = registerVisitorOperation.process(input);
        return handleResponse(result);
    }
    @GetMapping(HotelURLPaths.GET_REGISTER)
    @Operation(summary ="gives a report on a specific info" )
    public ResponseEntity<?> reportInfo( @RequestParam LocalDate startDate,
                                         @RequestParam LocalDate endDate,
                                         @RequestParam String roomNumber,
                                         @RequestParam( required = false) String firstName,
                                         @RequestParam( required = false) String lastName,
                                         @RequestParam( required = false) String phoneNumber,
                                         @RequestParam( required = false) String idCardNumber,
                                         @RequestParam( required = false) String idCardValidity,
                                         @RequestParam( required = false) String idCardIssueAuthority,
                                         @RequestParam( required = false) String idCardIssueDate) {
        SystemReportInput input = SystemReportInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .idCardNumber(idCardNumber)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNumber(roomNumber)
                .build();
        Either<ErrorWrapper, SystemRepostOutput> result = systemReportOperation.process(input);
        return handleResponse(result);
    }

    @PostMapping(HotelURLPaths.POST_SYSTEM_ROOM)
    @Operation(summary = "Adds a room DONE")
    public ResponseEntity<?> createRoom( @RequestBody CreateRoomInput input) {
        Either<ErrorWrapper, CreateRoomOutput> result = createRoomOperation.process(input);
        return handleResponse(result);
    }

    @PutMapping(HotelURLPaths.PUT_SYSTEM_ROOM_ID)
    @Operation(summary = "updates room DONE")
    public ResponseEntity<?> updateRoom( @RequestBody UpdateRoomInput input, @PathVariable UUID roomId) {
        UpdateRoomInput result = UpdateRoomInput.builder()
                .id(roomId)
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .roomNumber(input.getRoomNumber())
                .price(input.getPrice())
                .beds(input.getBeds())
                .build();
        Either<ErrorWrapper, UpdateRoomOutput> process = updateRoomOperation.process(result);
        return handleResponse(process);
    }

    @PatchMapping(value = HotelURLPaths.PATCH_SYSTEM_ROOM_ID, consumes = "application/json-patch+json")
    @Operation(summary = "partial updated the room DONE")
    public ResponseEntity<?> partialUpdateRoom(@RequestBody PartialUpdateRoomInput input,
                                               @PathVariable String roomId) {
        PartialUpdateRoomInput result = PartialUpdateRoomInput.builder()
                .id(UUID.fromString(roomId))
                .bathroomType(input.getBathroomType())
                .floor(input.getFloor())
                .roomNumber(input.getRoomNumber())
                .price(input.getPrice())
                .beds(input.getBeds())
                .build();
        Either<ErrorWrapper, PartialUpdateRoomOutput> process = partialUpdateRoomOperation.process(result);
        return handleResponse(process);
    }


    @DeleteMapping(HotelURLPaths.DELETE_SYSTEM_ROOM_ID)
    @Operation(summary = "deletes a room DONE")
    public ResponseEntity<?> deleteRoom( @PathVariable UUID roomId) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .roomId(roomId)
                .build();
        Either<ErrorWrapper, DeleteRoomOutput> process = deleteRoomOperation.process(input);
        return handleResponse(process);
    }
}
