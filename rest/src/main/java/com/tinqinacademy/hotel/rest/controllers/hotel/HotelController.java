package com.tinqinacademy.hotel.rest.controllers.hotel;


import com.tinqinacademy.hotel.api.models.exceptions.errorWrapper.ErrorWrapper;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsInput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOperation;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIdOperation;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.core.services.paths.HotelURLPaths;
import com.tinqinacademy.hotel.rest.controllers.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
public class HotelController extends BaseController {
    private final BookRoomOperation bookRoomOperation;
    private final GetFreeRoomsOperation getFreeRoomsOperation;
    private final GetRoomByIdOperation getRoomByIdOperation;
    private final UnbookRoomOperation unbookRoomOperation;


    @Autowired
    public HotelController(BookRoomOperation bookRoomOperation, GetFreeRoomsOperation getFreeRoomsOperation, GetRoomByIdOperation getRoomByIdOperation, UnbookRoomOperation unbookRoomOperation) {
        this.bookRoomOperation = bookRoomOperation;
        this.getFreeRoomsOperation = getFreeRoomsOperation;
        this.getRoomByIdOperation = getRoomByIdOperation;
        this.unbookRoomOperation = unbookRoomOperation;
    }


    @PostMapping(HotelURLPaths.POST_ROOM_ID)
    @Operation(summary = "books a rooms DONE")
    public ResponseEntity<?> bookRoom( @RequestBody BookRoomInput input,
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

    @GetMapping(HotelURLPaths.GET_ROOMS)
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

    @GetMapping(HotelURLPaths.GET_ROOM_ID)
    @Operation(summary = "returns basic info for a room DONE")
    public ResponseEntity<?> getRoomsById(@PathVariable UUID roomId) {
        GetRoomByIDInput input = GetRoomByIDInput.builder()
                .roomID(roomId)
                .build();
        Either<ErrorWrapper, GetRoomByIDOutput> result = getRoomByIdOperation.process(input);
        return handleResponse(result);
    }


    @DeleteMapping(HotelURLPaths.DELETE_BOOKING_ID)
    @Operation(summary = "unbooks a room DONE")
    public ResponseEntity<?> unbookRoom(@PathVariable("bookingId") UUID bookingId) {
        UnbookRoomInput build = UnbookRoomInput.builder()
                .bookingRoomId(bookingId)
                .build();
        Either<ErrorWrapper, UnbookRoomOutput> result = unbookRoomOperation.process(build);
        return handleResponse(result);
    }
}
