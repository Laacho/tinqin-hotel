package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BenEntityToBedEnum implements Converter<BedEntity, com.tinqinacademy.hotel.api.models.enums.Bed> {
    @Override
    public com.tinqinacademy.hotel.api.models.enums.Bed convert(BedEntity source) {
        return com.tinqinacademy.hotel.api.models.enums.Bed.getByCode(source.toString());
    }
}
