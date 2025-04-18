package com.example.hotel.mapper;


import com.example.hotel.entity.Hotel;
import com.example.hotel.model.request.CreateHotelRequest;
import com.example.hotel.model.response.CreateHotelResponse;
import com.example.hotel.model.response.GetHotelResponse;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class HotelMapper {

    // It is a basic sample class. We can use MapStruct, ModelMapper, or other general Mapper libraries.
    public static Hotel toHotel(CreateHotelRequest request) {
        return new Hotel(
                request.name(),
                request.address(),
                request.starRating(),
                new ArrayList<>()
        );
    }

    public static CreateHotelResponse toCreateHotelResponse(Hotel hotel) {
        return new CreateHotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getStarRating(),
                hotel.getCreatedAt(),
                hotel.getUpdatedAt()
        );
    }

    public static List<CreateHotelResponse> toListCreateHotelResponse(List<Hotel> hotels) {
        return hotels
                .stream()
                .map(HotelMapper::toCreateHotelResponse)
                .toList();
    }

    public static GetHotelResponse toGetHotelResponse(Hotel hotel) {
        return new GetHotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getStarRating(),
                hotel.getCreatedAt(),
                hotel.getUpdatedAt(),
                hotel.getVersion()
        );
    }

    public static Hotel hotelResponseToHotel(GetHotelResponse response) {
        return Hotel.builder()
                .id(response.id())
                .name(response.name())
                .address(response.address())
                .starRating(response.starRating())
                .createdAt(response.createdAt())
                .updatedAt(response.updatedAt())
                .version(response.version())
                .build();
    }
}