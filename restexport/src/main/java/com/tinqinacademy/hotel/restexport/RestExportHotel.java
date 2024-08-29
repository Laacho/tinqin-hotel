package com.tinqinacademy.hotel.restexport;

import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.createRoom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.deleteRoom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDOutput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.operations.registerVisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.models.operations.systemRepost.SystemRepostOutput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.models.operations.updateRoom.UpdateRoomOutput;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@FeignClient("hotel-service")
public interface RestExportHotel {
    //HOTEL
//    @PostMapping(URLPaths.ROOM_ID)
    @RequestLine("POST  /api/hotel/{roomId}?input={input}")
    BookRoomOutput bookRoom( @Param("input") BookRoomInput input,
                            @Param("roomId") UUID roomId);

    //@GetMapping(URLPaths.ROOMS)
    @RequestLine("GET /api/hotel/rooms?startDate={startDate}&endDate={endDate}&bed={beds}&bathRoomType={bathRoomType}")
    GetFreeRoomsOutput checkIfRoomIsFree(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("beds") List<String> beds,
                                         @Param("bathRoomType") String bathRoomType
    );

    //@GetMapping(URLPaths.ROOM_ID)
    @RequestLine("GET /api/hotel/{roomId}?roomId={roomId}")
    GetRoomByIDOutput getRoomsById(@Param("roomId") UUID roomId);

   // @DeleteMapping(URLPaths.BOOKING_ID)
    @RequestLine("DELETE /api/hotel/{bookingId}?bookingId={bookingId}")
    UnbookRoomOutput unbookRoom(@Param("bookingId") UUID bookingId);

    //SYSTEM
    //@PostMapping(URLPaths.SYSTEM_ROOM)
    @RequestLine("POST /api/system/room?input={input}")
    CreateRoomOutput createRoom( @Param CreateRoomInput input);

    //@PostMapping(URLPaths.REGISTER)
    @RequestLine("POST /api/system/register?input={input}")
    RegisterVisitorOutput registerVisitor( @Param("input") RegisterVisitorInput input);


    //@GetMapping(URLPaths.REGISTER)
    @RequestLine("GET /api/system/register?startDate={startDate}&endDate={endDate}&roomNumber={roomNumber}" +
                 "&firstName={firstName}&lastName={lastName}&phoneNumber={phoneNumber}" +
                 "&idCardNumber={idCardNumber}&idCardValidity={idCardValidity}&" +
                 "idCardIssueAuthority={idCardIssueAuthority}&idCardIssueDate={idCardIssueDate}")
    SystemRepostOutput reportInfo(       @Param("roomId") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("roomNumber") String roomNumber,
                                         @Param("firstName") String firstName,
                                         @Param("lastName") String lastName,
                                         @Param("phoneNumber") String phoneNumber,
                                         @Param("idCardNumber") String idCardNumber,
                                         @Param("idCardValidity") String idCardValidity,
                                         @Param("idCardIssueAuthority") String idCardIssueAuthority,
                                         @Param("idCardIssueDate") String idCardIssueDate);


    //@PutMapping(URLPaths.SYSTEM_ROOM_ID)
    @RequestLine("PUT  /api/system/room/{roomId}?input={input}&roomId={roomId}")
    UpdateRoomOutput updateRoom(@Param("input") UpdateRoomInput input, @Param("roomId") UUID roomId);

   //@PatchMapping(value = URLPaths.SYSTEM_ROOM_ID, consumes = "application/json-patch+json")
   @RequestLine("PATCH /api/system/room/{roomId}?input={input}?roomId={roomId}")
    PartialUpdateRoomOutput partialUpdateRoom(@Param("input") PartialUpdateRoomInput input,
                                               @Param("roomId") String roomId);

    //@DeleteMapping(URLPaths.SYSTEM_ROOM_ID)
    @RequestLine("DELETE /api/system/room/{roomId}?roomId={roomId}")
    DeleteRoomOutput deleteRoom( @Param("roomId") UUID roomId);
}
