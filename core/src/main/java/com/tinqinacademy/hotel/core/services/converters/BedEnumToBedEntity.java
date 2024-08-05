package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.enums.Bed;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BedEnumToBedEntity implements Converter<Bed, BedEntity> {
    @Override
    public BedEntity convert(Bed source) {
        return BedEntity.builder()
                .bedType(BedSize.getByCode(source.getCode()))
                .capacity(source.getCount())
                .build();

    }
}
