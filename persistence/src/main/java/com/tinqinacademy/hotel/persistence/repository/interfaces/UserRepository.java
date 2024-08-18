package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = """
            select * from userrs
            where userrs.first_name=:firstName
              and userrs.last_name=:lastName
              and userrs.phone_number=:phoneNumber
            """,nativeQuery = true)
    User findByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);


}
