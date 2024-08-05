package com.tinqinacademy.hotel.api.models.operations.unbookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UnbookRoomOutput implements OperationOutput {
private String message;
}
