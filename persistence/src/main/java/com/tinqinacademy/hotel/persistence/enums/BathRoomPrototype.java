package com.tinqinacademy.hotel.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BathRoomPrototype {
    PRIVATE("private"),
    SHARED("shared"),
    UNKNOWN("");
    private final String code;

    BathRoomPrototype(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BathRoomPrototype getByCode(String code) {
//        for (BathRoomType e : values()) {
//            // boolean b = ObjectUtils.containsConstant(BadRoomType.values(), code, false);
//            if (e.code.equals(code))
//                return e;
//        }
//        return UNKNOWN;
        //second variant with stream:
     return    Arrays.stream(BathRoomPrototype.values())
                .filter(e->e.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

    @JsonValue
    public String toString() {
        return this.code;
    }
}
