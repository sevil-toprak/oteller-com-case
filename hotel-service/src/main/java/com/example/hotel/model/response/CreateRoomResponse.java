package com.example.hotel.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateRoomResponse(
        Long id,
        Long hotelId,
        String roomNumber,
        int capacity,
        BigDecimal pricePerNight,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}