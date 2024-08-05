package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
}
