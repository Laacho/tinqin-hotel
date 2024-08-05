package com.tinqinacademy.hotel.persistence.BedSizeFill;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.interfaces.BedRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class BedSizeConfiguration implements ApplicationRunner {

    private final BedRepository bedRepository;

    public BedSizeConfiguration( BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        Map<String, Integer> result = bedRepository.findAll()
//                .stream()
//                .collect(Collectors.toMap(BedEntity::getBedType, BedEntity::getCapacity));
        Map<String, Integer> result=new HashMap<>();
        List<BedEntity> all = bedRepository.findAll();
        for (BedEntity bedEntity : all) {
            String code = bedEntity.getBedType().getCode();
            Integer capacity = bedEntity.getCapacity();
            result.put(code,capacity);
        }
        Map<String, Integer> origin = new HashMap<>();
        for (BedSize value : BedSize.values()) {
            String code = value.getCode();
            Integer count = value.getCount();
            origin.put(code, count);
        }

        for (Map.Entry<String, Integer> kvp : origin.entrySet()) {
            if (!result.containsKey(kvp.getKey())
                    && !result.containsValue(kvp.getValue())
            && !kvp.getKey().equalsIgnoreCase("unknown")
            ) {
                BedEntity bedEntity = BedEntity.builder()
                        .bedType(BedSize.getByCode(kvp.getKey()))
                        .capacity(kvp.getValue())
                        .build();
                bedRepository.save(bedEntity);
            }
        }

    }
    }

