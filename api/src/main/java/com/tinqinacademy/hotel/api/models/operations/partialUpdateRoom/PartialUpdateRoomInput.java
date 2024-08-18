package com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PartialUpdateRoomInput implements OperationInput {

    @Hidden
    private UUID id;
    private List<String> beds;
    @Size(min = 1, max = 15)
    private String bathroomType;
    @PositiveOrZero(message = "floor must be equal or greater to zero")
    @Max(10)
    private Integer floor;
    @Size(min = 1, max = 15)
    private String roomNumber;
    @PositiveOrZero
    private BigDecimal price;
}
