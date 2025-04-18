package com.example.hotel.exception.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ErrorMessages {

    public static final String NOT_FOUND = "Not found.";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error.";
    public static final String HOTEL_NOT_FOUND = "Hotel not found.";
    public static final String ROOM_NOT_FOUND = "Room not found.";
    public static final String ROOM_NOT_ASSOCIATED_WITH_HOTEL = "Room must be associated with a hotelId.";

}