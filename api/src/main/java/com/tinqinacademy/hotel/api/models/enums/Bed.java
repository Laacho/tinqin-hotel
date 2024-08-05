package com.tinqinacademy.hotel.api.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Bed {
    KING_SIZE("kingSize", 3),
    QUEEN_SIZE("queenSize", 4),
    SMALL_DOUBLE("smallDouble", 2),
    DOUBLE("double", 2),
    SINGLE("single", 1),
    UNKNOWN("unknown", 0);
    private final String code;
    private final Integer count;

    Bed(String code, Integer count) {
        this.code = code;
        this.count = count;
    }


    @JsonCreator
    public static Bed getByCode(String code) {
        //refactor with stream
        for (Bed e : values()) {
            if (e.code.equals(code))
                return e;
        }
        return UNKNOWN;
    }
    @JsonCreator
    public static boolean check(String code) {
        //refactor with stream
        for (Bed e : values()) {
            if (e.code.equals(code))
                return true;
        }
        return false;
    }

    @JsonValue
    public String toString() {
        return this.code;
    }




}
