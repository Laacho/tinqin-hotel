package com.tinqinacademy.hotel.api.models.operations.unbookRoom;


import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UnbookRoomInput implements OperationInput {
    @NotNull
    private UUID bookingRoomId;
}
