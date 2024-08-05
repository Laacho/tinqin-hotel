package com.tinqinacademy.hotel.api.models.operations.createRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateRoomInput implements OperationInput {

    private List<String> beds;
    @NotEmpty
    private String bathRoomType;
    @PositiveOrZero
    private Integer floor;
    @Size(min = 1, max = 5)
    private String roomNumber;
    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;

}
