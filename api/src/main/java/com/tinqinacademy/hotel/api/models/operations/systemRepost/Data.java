package com.tinqinacademy.hotel.api.models.operations.systemRepost;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Data {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String idCardNumber;
    private LocalDate idCardValidity;
    private String idCardIssueAuthority;
    private LocalDate idCardIssueDate;
    private LocalDate birthdate;

}
