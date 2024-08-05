package com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PartialUpdateRoomOutput implements OperationOutput {
    private UUID id;
}
