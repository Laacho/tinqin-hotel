package com.tinqinacademy.hotel.persistence.entities;

import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer floor;

    @Column(name = "bathroom_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private BathRoomPrototype bathroomPrototype;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name ="room_number",nullable = false )
    private String roomNumber;

    @ManyToMany
    @JoinTable(name = "rooms_to_beds")
    private List<BedEntity> beds;
}
