package com.tinqinacademy.hotel.api.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BathRoomType {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");
    private final String code;

    BathRoomType(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BathRoomType getByCode(String code) {

//        for (BathRoomType e : values()) {
//            // boolean b = ObjectUtils.containsConstant(BadRoomType.values(), code, false);
//            if (e.code.equals(code))
//                return e;
//        }
//        return UNKNOWN;
        //second variant with stream:
     return    Arrays.stream(BathRoomType.values())
                .filter(e->e.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

    @JsonValue
    public String toString() {
        return this.code;
    }
}
