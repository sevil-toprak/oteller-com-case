package com.example.hotel.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

public record CreateHotelRequest(
        @NotBlank(message = "Hotel name is required")
        @Size(max = 100, message = "Hotel name must be less than 100 characters")
        String name,

        @Size(max = 255, message = "Address must be less than 255 characters")
        String address,

        @Min(value = 1, message = "Star rating must be at least 1")
        @Max(value = 5, message = "Star rating must be at most 5")
        int starRating,

        List<String> rooms
) implements Serializable {
}