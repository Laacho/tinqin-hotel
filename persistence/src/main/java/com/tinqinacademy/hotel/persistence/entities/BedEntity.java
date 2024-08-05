package com.tinqinacademy.hotel.persistence.entities;

import com.tinqinacademy.hotel.persistence.enums.BedSize;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "beds")
public class BedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private BedSize bedType;
    @Column(name = "people_count",nullable = false)
    private Integer capacity;
}
