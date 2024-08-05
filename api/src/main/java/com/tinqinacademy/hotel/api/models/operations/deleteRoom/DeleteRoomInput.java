package com.tinqinacademy.hotel.api.models.operations.deleteRoom;


import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeleteRoomInput implements OperationInput {
    @NotNull
    private UUID roomId;
}
