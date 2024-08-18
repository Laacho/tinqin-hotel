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
    @NotNull(message = "first name cannot be null")
    private String firstName;
    @Size(min=1,max =15 )
    @NotNull(message = "last name cannot be null")
    private String lastName;
    @Size(min=1,max =15 )
    @NotNull(message = "phone number cannot be null")
    private String phoneNumber;

    @Size(min = 1,max = 20)
    private String idCardNumber;
    @NotNull(message = "id card validity cannot be null")
    private String idCardValidity;
    @Size(min = 1,max = 100)
    @NotNull(message = "issue authority cannot be null")
    private String idCardIssueAuthority;
    @NotNull(message = "issue date cannot be null")
    private String idCardIssueDate;
    @Size(min = 1,max=50)
    @NotNull(message = "room number cannot be null")
    private String roomNumber;

    @NotNull(message = "birthdate cannot be null")
    private String birthdate;

}
