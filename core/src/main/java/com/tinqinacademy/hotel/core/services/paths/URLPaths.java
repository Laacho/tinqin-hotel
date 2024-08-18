package com.tinqinacademy.hotel.core.services.paths;

public class URLPaths {
    //hotel urls
    private static final String STARTER_URL="/api";
    public static final String ROOM_ID=STARTER_URL+"/hotel/{roomId}";

    public static final String ROOMS=STARTER_URL+"/hotel/rooms";
    public static final String BOOKING_ID=STARTER_URL+"/hotel/{bookingId}";

    //system urls
    public static final String REGISTER=STARTER_URL+"/system/register";
    public static final String SYSTEM_ROOM=STARTER_URL+"/system/room";

    public static final String SYSTEM_ROOM_ID=SYSTEM_ROOM+"/{roomId}";




}
