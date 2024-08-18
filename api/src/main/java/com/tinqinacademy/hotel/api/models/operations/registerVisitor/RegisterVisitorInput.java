package com.tinqinacademy.hotel.api.models.operations.registerVisitor;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RegisterVisitorInput implements OperationInput {


    @FutureOrPresent(message = "must be a valid date")
    private LocalDate startDate;
    @FutureOrPresent(message = "must be a valid date")
    private LocalDate endDate;

    @NotNull(message = "room number must not be null")
    private String roomNumber;
    private List<DataForEachVisitor> dataForVisitors;

}
