package com.example.hotel.service.spec;

import com.example.hotel.entity.Room;
import com.example.hotel.model.request.CreateRoomRequest;
import com.example.hotel.model.response.CreateRoomResponse;
import com.example.hotel.model.response.GetRoomResponse;

import java.util.List;

public interface RoomService {

    CreateRoomResponse createRoom(CreateRoomRequest request);

    List<GetRoomResponse> getRoomList();

    GetRoomResponse getRoomById(Long id);

    GetRoomResponse updateRoom(Long id, Room updatedData);

    void deleteRoom(Long id);
}
