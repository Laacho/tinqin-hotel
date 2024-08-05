package com.tinqinacademy.hotel.api.models.operations.createRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateRoomOutput implements OperationOutput{
    private UUID id;
}
