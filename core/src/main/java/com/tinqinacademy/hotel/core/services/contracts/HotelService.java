package com.tinqinacademy.hotel.core.services.contracts;

import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.bookRoom.BookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.models.operations.unbookRoom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsInput;
import com.tinqinacademy.hotel.api.models.operations.getFreeRooms.GetFreeRoomsOutput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDInput;
import com.tinqinacademy.hotel.api.models.operations.getRoomByID.GetRoomByIDOutput;

public interface HotelService {
    GetFreeRoomsOutput getFreeRooms(GetFreeRoomsInput input);
    GetRoomByIDOutput getRoomById(GetRoomByIDInput input);

    UnbookRoomOutput unbookRoom(UnbookRoomInput input);

    BookRoomOutput bookRoom(BookRoomInput input);

}
