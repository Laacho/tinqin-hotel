package com.tinqinacademy.hotel.api.models.operations.registerVisitor;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
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


    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;

    private List<DataForEachVisitor> dataForVisitors;

}
