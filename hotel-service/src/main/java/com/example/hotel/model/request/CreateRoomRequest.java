package com.example.hotel.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

public record CreateRoomRequest(
        @NotBlank(message = "Hotel id is required")
        Long hotelId,

        @Size(max = 10, message = "Room number must be less than 10 characters")
        String roomNumber,

        int capacity,

        @NotBlank(message = "Hotel id is required")
        BigDecimal pricePerNight

) implements Serializable {
}