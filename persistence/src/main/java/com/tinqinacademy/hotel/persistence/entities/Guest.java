package com.tinqinacademy.hotel.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "id_card_number",nullable = false)
    private String idCardNumber;
    @Column(name = "id_cart_validity",nullable = false)
    private LocalDate idCardValidity;
    @Column(name = "id_card_authority",nullable = false)
    private String idCardAuthority;
    @Column(name = "id_card_issue_date",nullable = false)
    private LocalDate idCardIssueDate;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "birthdate",nullable = false)
    private LocalDate birthday;


}
