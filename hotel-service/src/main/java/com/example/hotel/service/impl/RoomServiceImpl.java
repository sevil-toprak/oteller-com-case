package com.example.hotel.service.impl;

import com.example.hotel.entity.Hotel;
import com.example.hotel.entity.Room;
import com.example.hotel.exception.BaseException;
import com.example.hotel.exception.constants.ErrorCode;
import com.example.hotel.exception.constants.ErrorMessages;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.mapper.RoomMapper;
import com.example.hotel.model.request.CreateRoomRequest;
import com.example.hotel.model.response.CreateRoomResponse;
import com.example.hotel.model.response.GetHotelResponse;
import com.example.hotel.model.response.GetRoomResponse;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.service.spec.HotelService;
import com.example.hotel.service.spec.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    @Override
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        GetHotelResponse hotelResponse = hotelService.getHotelById(request.hotelId());
        // This line necessary, because of business service is missing.
        Hotel hotel = HotelMapper.hotelResponseToHotel(hotelResponse);

        if (Objects.isNull(hotel)) {
            throw new BaseException(ErrorCode.ROOM_NOT_ASSOCIATED_WITH_HOTEL, ErrorMessages.ROOM_NOT_ASSOCIATED_WITH_HOTEL, HttpStatus.NOT_FOUND);
        }

        Room room = RoomMapper.toRoom(request);
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);
        return RoomMapper.toCreateRoomResponse(savedRoom);
    }

    @Override
    public List<GetRoomResponse> getRoomList() {
        List<Room> roomList = roomRepository.findAll();
        return RoomMapper.toListCreateRoomResponse(roomList);
    }

    @Override
    public GetRoomResponse getRoomById(Long id) {
        Room room = getRoom(id);
        return RoomMapper.toGetRoomResponse(room);
    }

    @Override
    @Transactional
    public GetRoomResponse updateRoom(Long id, Room updatedData) {
        Room existingRoom = getRoom(id);
//        existingRoom.setHotelId(updatedData.getHotelId());
        existingRoom.setRoomNumber(updatedData.getRoomNumber());
        existingRoom.setCapacity(updatedData.getCapacity());
        existingRoom.setPricePerNight(updatedData.getPricePerNight());
        Room room = roomRepository.save(existingRoom);
        return RoomMapper.toGetRoomResponse(room);
    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        Room existingRoom = getRoom(id);
        roomRepository.delete(existingRoom);
    }

    private Room getRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND, ErrorMessages.ROOM_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}