package com.tinqinacademy.hotel.rest;


import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsInput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOperation;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIdOperation;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOperation;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportInput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemReportOperation;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemRepostOutput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOutput;
import com.tinqinacademy.hotel.rest.paths.URLPaths;
import io.swagger.v3.oas.annotations.Operation;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class HotelController extends BaseController {
    private final DeleteRoomOperation deleteRoomOperation;
    private final PartialUpdateRoomOperation partialUpdateRoomOperation;
    private final BookRoomOperation bookRoomOperation;
    private final GetFreeRoomsOperation getFreeRoomsOperation;
    private final GetRoomByIdOperation getRoomByIdOperation;
    private final UnbookRoomOperation unbookRoomOperation;
    private final RegisterVisitorOperation registerVisitorOperation;
    private final SystemReportOperation systemReportOperation;
    private final CreateRoomOperation createRoomOperation;
    private final UpdateRoomOperation updateRoomOperation;


    @Autowired
    public HotelController(DeleteRoomOperation deleteRoomOperation, PartialUpdateRoomOperation partialUpdateRoomOperation, BookRoomOperation bookRoomOperation, GetFreeRoomsOperation getFreeRoomsOperation, GetRoomByIdOperation getRoomByIdOperation, UnbookRoomOperation unbookRoomOperation, RegisterVisitorOperation registerVisitorOperation, SystemReportOperation systemReportOperation, CreateRoomOperation createRoomOperation, UpdateRoomOperation updateRoomOperation) {
        this.deleteRoomOperation = deleteRoomOperation;
        this.partialUpdateRoomOperation = partialUpdateRoomOperation;
        this.bookRoomOperation = bookRoomOperation;
        this.getFreeRoomsOperation = getFreeRoomsOperation;
        this.getRoomByIdOperation = getRoomByIdOperation;
        this.unbookRoomOperation = unbookRoomOperation;
        this.registerVisitorOperation = registerVisitorOperation;
        this.systemReportOperation = systemReportOperation;
        this.createRoomOperation = createRoomOperation;
        this.updateRoomOperation = updateRoomOperation;
    }


    @PostMapping(URLPaths.ROOM_ID)
    @Operation(summary = "books a rooms DONE")
    public ResponseEntity<?> bookRoom(@Valid @RequestBody BookRoomInput input,
                                      @PathVariable UUID roomId) {

        BookRoomInput bookRoom = BookRoomInput.builder()
                .roomId(roomId)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .birthdate(input.getBirthdate())
                .email(input.getEmail())
                .build();
        Either<ErrorWrapper, BookRoomOutput> result = bookRoomOperation.process(bookRoom);
        return handleResponse(result);
    }

    @GetMapping(URLPaths.ROOMS)
    @Operation(summary = "checks if a room is free DONE")
    public ResponseEntity<?> checkIfRoomIsFree(@RequestParam LocalDate startDate,
                                               @RequestParam LocalDate endDate,
                                               @RequestParam List<String> beds,
                                               @RequestParam String bathRoomType
    ) {
        GetFreeRoomsInput input = GetFreeRoomsInput.builder()
                .bathRoomType(bathRoomType)
                .startDate(startDate)
                .endDate(endDate)
                .beds(beds)
                .build();
        Either<ErrorWrapper, GetFreeRoomsOutput> result = getFreeRoomsOperation.process(input);
        return handleResponse(result);
    }

    @GetMapping(URLPaths.ROOM_ID)
    @Operation(summary = "returns basic info for a room DONE")
    public ResponseEntity<?> getRoomsById(@PathVariable UUID roomId) {
        GetRoomByIDInput input = GetRoomByIDInput.builder()
                .roomID(roomId)
                .build();
        Either<ErrorWrapper, GetRoomByIDOutput> result = getRoomByIdOperation.process(input);
        return handleResponse(result);
    }

//    @PostMapping(URLPaths.ROOM_ID)
//    public ResponseEntity<PostRoomByIdOutput> postSpecificRoom(@PathVariable("roomId") UUID roomID
//            , @Valid @RequestBody PostRoomByIdInput input) {
//        PostRoomByIdInput result = PostRoomByIdInput.builder()
//                .startDate(input.getStartDate())
//                .endDate(input.getEndDate())
//                .firstName(input.getFirstName())
//                .lastName(input.getLastName())
//                .phoneNumber(input.getPhoneNumber())
//                .roomId(roomID)
//                .build();
//        return ResponseEntity.ok(hotelService.postSpecificRoom(result));
//    }

    @DeleteMapping(URLPaths.BOOKING_ID)
    @Operation(summary = "unbooks a room DONE")
    public ResponseEntity<?> unbookRoom(@PathVariable("bookingId") UUID bookingId) {
        UnbookRoomInput build = UnbookRoomInput.builder()
                .bookingRoomId(bookingId)
                .build();
        Either<ErrorWrapper, UnbookRoomOutput> result = unbookRoomOperation.process(build);
        return handleResponse(result);
    }

    @PostMapping(URLPaths.REGISTER)
    @Operation(summary = "registers a visitor DONE")
    public ResponseEntity<?> registerVisitor(@Valid @RequestBody RegisterVisitorInput input) {
        Either<ErrorWrapper, RegisterVisitorOutput> result = registerVisitorOperation.process(input);
        return handleResponse(result);
    }

    @GetMapping(URLPaths.REGISTER)
    public ResponseEntity<?> reportInfo(@Valid @RequestBody SystemReportInput input) {
        Either<ErrorWrapper, SystemRepostOutput> result = systemReportOperation.process(input);
        return handleResponse(result);
    }

    @PostMapping(URLPaths.SYSTEM_ROOM)
    @Operation(summary = "Adds a room DONE")
    public ResponseEntity<?> createRoom(@Valid @RequestBody CreateRoomInput input) {
        Either<ErrorWrapper, CreateRoomOutput> result = createRoomOperation.process(input);
        return handleResponse(result);
    }

    @PutMapping(URLPaths.SYSTEM_ROOM_ID)
    @Operation(summary = "updates room DONE")
    public ResponseEntity<?> updateRoom(@Valid @RequestBody UpdateRoomInput input, @PathVariable UUID roomId) {
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

    @PatchMapping(value = URLPaths.SYSTEM_ROOM_ID, consumes = "application/json-patch+json")
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

    @DeleteMapping(URLPaths.SYSTEM_ROOM_ID)
    @Operation(summary = "deletes a room DONE")
    public ResponseEntity<?> deleteRoom(@Valid @PathVariable UUID roomId) {
        DeleteRoomInput input = DeleteRoomInput.builder()
                .roomId(roomId)
                .build();
        Either<ErrorWrapper, DeleteRoomOutput> process = deleteRoomOperation.process(input);
        return handleResponse(process);
    }
}
