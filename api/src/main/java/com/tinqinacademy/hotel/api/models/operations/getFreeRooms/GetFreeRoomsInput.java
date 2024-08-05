package com.tinqinacademy.hotel.api.models.operations.getFreeRooms;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetFreeRoomsInput implements OperationInput {
    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    private List<String> beds;
    @NotBlank
    private String bathRoomType;
}
