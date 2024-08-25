package com.tinqinacademy.hotel.core.services.paths;

public class HotelURLPaths {
    //hotel urls
    public static final String POST_ROOM_ID="/api/hotel/{roomId}";
    public static final String GET_ROOM_ID="/api/hotel/{roomId}";
    public static final String GET_ROOMS ="/api/hotel/rooms";
    public static final String DELETE_BOOKING_ID="/api/hotel/{bookingId}";

    //system urls
    public static final String POST_REGISTER="/api/system/register";
    public static final String GET_REGISTER="/api/system/register";
    public static final String POST_SYSTEM_ROOM="/api/system/room";
    public static final String DELETE_SYSTEM_ROOM_ID="/api/system/room/{roomId}";
    public static final String PUT_SYSTEM_ROOM_ID="/api/system/room/{roomId}";
    public static final String PATCH_SYSTEM_ROOM_ID="/api/system/room/{roomId}";

}
