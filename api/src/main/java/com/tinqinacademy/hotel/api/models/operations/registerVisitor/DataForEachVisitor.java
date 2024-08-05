package com.tinqinacademy.hotel.api.models.operations.registerVisitor;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataForEachVisitor {
    @Size(min = 1,max = 15)
    @NotNull
    private String firstName;
    @Size(min = 1,max = 15)
    @NotNull
    private String lastName;
    @Size(min = 1,max = 10)
    @NotNull
    private String phoneNumber;
    @Size(min = 1,max = 10)
    @NotNull
    private String idCardNumber;
    @FutureOrPresent
    private LocalDate idCardValidity;
    @Size(min = 1,max = 15)
    @NotNull
    private String idCardIssueAuthority;
    @Past
    private LocalDate idCardIssueDate;

    @Past
    private LocalDate birthdate;
}
