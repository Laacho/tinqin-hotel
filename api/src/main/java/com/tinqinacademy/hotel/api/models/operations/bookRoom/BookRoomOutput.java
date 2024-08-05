package com.tinqinacademy.hotel.api.models.operations.bookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookRoomOutput implements OperationOutput {
    private String message;
}
