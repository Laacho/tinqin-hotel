package com.tinqinacademy.hotel.api.models.operations.bookRoom;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookRoomInput implements OperationInput {
    @Hidden
    private UUID roomId;
    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;
    @Size(min=1,max = 10)
    @NotNull
    private String firstName;
    @Size(min=1,max = 10)
    @NotNull
    private String lastName;
    @Size(min=1,max = 10)
    @NotNull
    private String phoneNumber;
    @Past
    private LocalDate birthdate;
    @Size(min = 5,max = 30)
    private String email;
}
