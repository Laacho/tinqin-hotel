package com.tinqinacademy.hotel.persistence.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public enum BedSize {
    KING_SIZE("kingSize", 3),
    QUEEN_SIZE("queenSize", 4),
    SMALL_DOUBLE("smallDouble", 2),
    DOUBLE("double", 2),
    SINGLE("single", 1),
    UNKNOWN("unknown", 0);
    private final String code;
    private final Integer count;

    BedSize(String code, Integer count) {
        this.code = code;
        this.count = count;
    }


    @JsonCreator
    public static BedSize getByCode(String code) {
        //refactor with stream
        for (BedSize e : values()) {
            if (e.code.equals(code))
                return e;
        }
        return UNKNOWN;
    }
    @JsonCreator
    public static boolean check(String code) {
        //refactor with stream
        for (BedSize e : values()) {
            if (e.code.equals(code))
                return true;
        }
        return false;
    }

    @JsonValue
    public String toString() {
        return this.code;
    }

    public static BedSize fromResultSet(ResultSet rs) throws SQLException {
        String code = rs.getString("bed_type");
        int capacity = rs.getInt("capacity");
        for (BedSize bedSize : BedSize.values()) {
            if (bedSize.code.equals(code) && bedSize.count == capacity)
                return bedSize;
        }
        throw new IllegalArgumentException("No bed found");
    }


}
