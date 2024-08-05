package com.tinqinacademy.hotel.api.models.operations.systemRepost;

import com.tinqinacademy.hotel.api.models.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SystemReportInput implements OperationInput {
    @FutureOrPresent(message = "Must be valid start date")
    private LocalDate startDate;
    @FutureOrPresent(message = "Must be a valid end date")
    private LocalDate endDate;
    @Size(min=1,max =15 )
    @NotNull
    private String firstName;
    @Size(min=1,max =15 )
    @NotNull
    private String lastName;
    @Size(min=1,max =15 )
    @NotNull
    private String phoneNumber;

    @Size(min = 1,max = 20)
    private String idCardNumber;
    @FutureOrPresent(message = "Must be a valid id date")
    private LocalDate idCardValidity;
    @Size(min = 1,max = 100)
    @NotNull
    private String idCardIssueAuthority;
    @FutureOrPresent(message = "Must be a valid id date")
    @NotNull
    private LocalDate idCardIssueDate;
    @Size(min = 1,max=50)
    @NotNull
    private String roomNumber;

}
