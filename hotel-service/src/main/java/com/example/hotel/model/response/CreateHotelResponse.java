package com.example.hotel.model.response;

import java.time.LocalDateTime;

public record CreateHotelResponse(
        Long id,
        String name,
        String address,
        int starRating,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}