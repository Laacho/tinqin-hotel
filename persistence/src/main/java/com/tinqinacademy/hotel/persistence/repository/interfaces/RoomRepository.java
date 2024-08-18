package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    String query = """
            select *
            from rooms
            where id not in (select room_id
                             from reservations)
            """;

    @Query(value = query, nativeQuery = true)
    List<Room> findRoomsByMe();


    Optional<Room> findRoomById(UUID id);


    Optional<Room> findRoomByRoomNumber(String roomNumber);


}
