package com.example.hotel.mapper;


import com.example.hotel.entity.Room;
import com.example.hotel.model.request.CreateRoomRequest;
import com.example.hotel.model.response.CreateRoomResponse;
import com.example.hotel.model.response.GetRoomResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RoomMapper {

    // It is a basic sample class. We can use MapStruct, ModelMapper, or other general Mapper libraries.
    public static Room toRoom(CreateRoomRequest request) {
        return new Room(
                request.roomNumber(),
                request.capacity(),
                request.pricePerNight(),
                null
        );
    }

    public static CreateRoomResponse toCreateRoomResponse(Room room) {
        return new CreateRoomResponse(
                room.getId(),
                room.getHotel().getId(),
                room.getRoomNumber(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }

    public static List<GetRoomResponse> toListCreateRoomResponse(List<Room> rooms) {
        return rooms
                .stream()
                .map(RoomMapper::toGetRoomResponse)
                .toList();
    }

    public static GetRoomResponse toGetRoomResponse(Room room) {
        return new GetRoomResponse(
                room.getId(),
                room.getHotel().getId(),
                room.getRoomNumber(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }
}