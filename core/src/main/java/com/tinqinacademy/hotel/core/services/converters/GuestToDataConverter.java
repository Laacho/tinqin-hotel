package com.tinqinacademy.hotel.core.services.converters;

import com.tinqinacademy.hotel.api.models.operations.registerVisitor.DataForEachVisitor;
import com.tinqinacademy.hotel.persistence.entities.Guest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GuestToDataConverter implements Converter<DataForEachVisitor, Guest> {
    @Override
    public Guest convert(DataForEachVisitor dataForVisitor) {
        return Guest.builder()
                .firstName(dataForVisitor.getFirstName())
                .lastName(dataForVisitor.getLastName())
                .birthday(dataForVisitor.getBirthdate())
                .idCardAuthority(dataForVisitor.getIdCardIssueAuthority())
                .idCardNumber(dataForVisitor.getIdCardNumber())
                .idCardValidity(dataForVisitor.getIdCardValidity())
                .idCardIssueDate(dataForVisitor.getIdCardIssueDate())
                .build();
    }
}
