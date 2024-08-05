package com.tinqinacademy.hotel.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "price")
    private BigDecimal price;

    @OneToOne(targetEntity = Room.class)
    private Room room;

    @ManyToOne
    private User user;

    @ManyToMany(targetEntity = Guest.class)
    @JoinTable(name = "reservations_to_guests")
    private List<Guest> guests;


}
