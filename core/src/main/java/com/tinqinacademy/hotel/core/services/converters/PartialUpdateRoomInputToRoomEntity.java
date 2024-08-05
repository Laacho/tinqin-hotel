package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.partialUpdateRoom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.persistence.entities.BedEntity;
import com.tinqinacademy.hotel.persistence.entities.Room;
import com.tinqinacademy.hotel.persistence.enums.BathRoomPrototype;
import com.tinqinacademy.hotel.persistence.enums.BedSize;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartialUpdateRoomInputToRoomEntity implements Converter<PartialUpdateRoomInput, Room> {
    @Override
    public Room convert(PartialUpdateRoomInput source) {

        BathRoomPrototype bathRoomPrototype = BathRoomPrototype.getByCode(source.getBathroomType());
        if(BathRoomPrototype.getByCode(source.getBathroomType()).equals(BathRoomPrototype.UNKNOWN)) {
            bathRoomPrototype = null;
        }

        return Room.builder()
                .floor(source.getFloor())
                .bathroomPrototype(BathRoomPrototype.getByCode(source.getBathroomType()))
                .roomNumber(source.getRoomNumber())
                .price(source.getPrice())
                .bathroomPrototype(bathRoomPrototype)
                .build();
    }
}
