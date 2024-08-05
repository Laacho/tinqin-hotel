package com.tinqinacademy.hotel.api.models.operations.getFreeRooms;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetFreeRoomsOutput implements OperationOutput {
    private List<UUID> ids;
}
