package com.tinqinacademy.hotel.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "userrs")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    @Column(name = "birthdate",nullable = false)
    private LocalDate birthday;
    @Column(name = "email")
    private String email;

    @OneToMany(targetEntity = Reservation.class,mappedBy = "user")
    private List<Reservation> reservations;
}
