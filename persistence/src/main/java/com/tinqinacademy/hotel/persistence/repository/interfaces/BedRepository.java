package com.tinqinacademy.hotel.persistence.repository.interfaces;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BedRepository extends JpaRepository<BedEntity,UUID> {

 Optional<BedEntity> findBedByBedType(BedSize bedType);
 String query= """
         SELECT b.id,b.type,b.people_count
                     FROM beds b
                     WHERE b.type = :type
         """;
 @Query(value =query,nativeQuery = true)
 BedEntity findEntityByType(String type);
 BedEntity findBedEntityByBedType(BedSize bedType);
 }
