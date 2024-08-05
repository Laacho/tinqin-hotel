package com.tinqinacademy.hotel.api.models.operations.getRoomByID;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import com.tinqinacademy.hotel.api.models.enums.BathRoomType;
import com.tinqinacademy.hotel.api.models.enums.Bed;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetRoomByIDOutput implements OperationOutput {
    private UUID id;
    private BigDecimal price;
    private Integer floor;
    private List<Bed> bed;
    private BathRoomType bathRoomType;
    private Map<LocalDate,LocalDate> datesOccupied;
}
