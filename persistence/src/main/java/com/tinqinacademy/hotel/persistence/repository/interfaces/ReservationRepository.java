package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    //first query checking if a room is free
    List<Reservation> findReservationByEndDateAfter(LocalDate startDate);

    //second query giving info for a room by id
    List<Reservation> findAllByRoomIdOrderByStartDate(UUID room_id);

//    @Query(value = """
//            select r.id, r.end_date, r.price, r.start_date, r.room_id, r.user_id
//            from reservations as r
//            where r.room_id = :roomId
//            """,nativeQuery = true)
    Optional<Reservation> findReservationByRoomId(UUID roomId);


}
