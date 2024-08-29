package com.tinqinacademy.hotel.api.models.operations.systemRepost;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SystemRepostOutput implements OperationOutput {
    private LocalDate startDate;
    private LocalDate endDate;
   private List<Data> data;
}
