package com.tinqinacademy.hotel.api.models.operations.updateRoom;

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
@ToString
@Builder
public class UpdateRoomInput implements OperationInput {

    @Hidden
    private UUID id;
    private List<String> beds;

    @Size(min = 1, max = 15,message = "invalid bathroom type")
    @NotNull(message = "bathroomType must not be null")
    private String bathroomType;

    @PositiveOrZero(message = "floor must greater or equal to zero")
    @Max(value = 10,message = "over max value")
    @NotNull(message = "floor must not be null")
    private Integer floor;

    @Size(min = 1, max = 10,message = "invalid room number")
    @NotNull(message = "room number cannot be null")
    private String roomNumber;

    @PositiveOrZero(message = "price must greater or equal to zero")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;
}
