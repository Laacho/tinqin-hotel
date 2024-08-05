package com.tinqinacademy.hotel.core.services.contracts;

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

import java.util.List;

public interface SystemService {

    RegisterVisitorOutput registerVisitor(List<RegisterVisitorInput> input);

    SystemRepostOutput reportRooms(SystemReportInput input);

    CreateRoomOutput createRoom(CreateRoomInput input);

    UpdateRoomOutput updateRoom(UpdateRoomInput input);

    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);

    DeleteRoomOutput deleteRoom(DeleteRoomInput input);
}
