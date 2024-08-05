package com.tinqinacademy.hotel.api.models.operations.updateRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateRoomOutput implements OperationOutput {
    private UUID id;
}
