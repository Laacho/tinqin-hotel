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
    @NotNull(message = "cannot be null")
    private String firstName;

    @Size(min = 1,max = 15)
    @NotNull(message = "cannot be null")
    private String lastName;

    @Size(min = 1,max = 10)
    @NotNull(message = "cannot be null")
    private String phoneNumber;

    @Size(min = 1,max = 10)
    @NotNull(message = "cannot be null")
    private String idCardNumber;

    @FutureOrPresent(message = "must be a valid date")
    private LocalDate idCardValidity;
    @Size(min = 1,max = 15)
    @NotNull(message = "issue authority cannot be null")
    private String idCardIssueAuthority;
    @Past(message = "must be a past date")
    private LocalDate idCardIssueDate;

    @Past(message = "birthdate must be a past date")
    private LocalDate birthdate;
}
