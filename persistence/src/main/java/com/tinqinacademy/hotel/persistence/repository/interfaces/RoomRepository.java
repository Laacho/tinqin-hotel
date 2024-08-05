package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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


    @Modifying
    @Transactional
    @Query("Update Room set bathroomPrototype=:bathroomType,floor=:floor,price=:price,roomNumber=:roomNumber where id=:id")
    void updateRoom(BathRoomPrototype bathroomType,Integer floor,BigDecimal price,String roomNumber,UUID id);



}
