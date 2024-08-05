package com.tinqinacademy.hotel.api.models.operations.deleteRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeleteRoomOutput implements OperationOutput {
    private String message;
}
