package com.tinqinacademy.hotel.api.models.operations.getRoomByID;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetRoomByIDInput implements OperationInput {
    @NotNull
    private UUID roomID;
}
