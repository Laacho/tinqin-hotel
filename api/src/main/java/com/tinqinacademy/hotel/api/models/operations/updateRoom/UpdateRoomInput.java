package com.tinqinacademy.hotel.api.models.operations.updateRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.enums.Bed;
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
@ToString
@Builder
public class UpdateRoomInput implements OperationInput {

    @Hidden
    private UUID id;
    private List<String> beds;
    @Size(min = 1, max = 15)
    @NotNull
    private String bathroomType;
    @PositiveOrZero
    @Max(10)
    private Integer floor;

    @Size(min = 1, max = 10)
    @NotNull
    private String roomNumber;
    @PositiveOrZero
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;
}
