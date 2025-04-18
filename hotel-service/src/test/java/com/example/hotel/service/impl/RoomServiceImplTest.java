package com.example.hotel.service.impl;

import com.example.hotel.entity.Hotel;
import com.example.hotel.entity.Room;
import com.example.hotel.exception.BaseException;
import com.example.hotel.exception.constants.ErrorCode;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.mapper.RoomMapper;
import com.example.hotel.model.request.CreateRoomRequest;
import com.example.hotel.model.response.CreateRoomResponse;
import com.example.hotel.model.response.GetHotelResponse;
import com.example.hotel.model.response.GetRoomResponse;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.service.spec.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void testCreateRoom_whenValidRequest_thenReturnResponse() {
        CreateRoomRequest request = new CreateRoomRequest(1L, "101", 2, BigDecimal.valueOf(120.00));
        GetHotelResponse hotelResponse = new GetHotelResponse(1L, "Hotel Name", "Address", 4, null, null, 1L);
        Hotel hotelEntity = new Hotel();
        Room roomEntity = new Room();
        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setHotel(hotelEntity);
        savedRoom.setRoomNumber("101");
        savedRoom.setCapacity(2);
        savedRoom.setPricePerNight(BigDecimal.valueOf(120.00));
        savedRoom.setCreatedAt(LocalDateTime.now());
        savedRoom.setUpdatedAt(LocalDateTime.now());

        CreateRoomResponse expectedResponse = new CreateRoomResponse(
                savedRoom.getId(),
                1L,
                savedRoom.getRoomNumber(),
                savedRoom.getCapacity(),
                savedRoom.getPricePerNight(),
                savedRoom.getCreatedAt(),
                savedRoom.getUpdatedAt()
        );

        try (MockedStatic<HotelMapper> mockedHotelMapper = mockStatic(HotelMapper.class);
             MockedStatic<RoomMapper> mockedRoomMapper = mockStatic(RoomMapper.class)) {

            when(hotelService.getHotelById(1L)).thenReturn(hotelResponse);
            mockedHotelMapper.when(() -> HotelMapper.hotelResponseToHotel(hotelResponse)).thenReturn(hotelEntity);
            mockedRoomMapper.when(() -> RoomMapper.toRoom(request)).thenReturn(roomEntity);
            when(roomRepository.save(roomEntity)).thenReturn(savedRoom);
            mockedRoomMapper.when(() -> RoomMapper.toCreateRoomResponse(savedRoom)).thenReturn(expectedResponse);

            CreateRoomResponse response = roomService.createRoom(request);

            assertNotNull(response);
            assertEquals("101", response.roomNumber());
            assertEquals(2, response.capacity());
            assertEquals(BigDecimal.valueOf(120.00), response.pricePerNight());
            verify(roomRepository).save(roomEntity);
        }
    }

    @Test
    void testGetRoomList_whenRoomsExist_thenReturnList() {
        Room room1 = new Room();
        room1.setId(1L);
        room1.setHotel(new Hotel());
        room1.setRoomNumber("101");
        room1.setCapacity(2);
        room1.setPricePerNight(BigDecimal.valueOf(100));
        room1.setCreatedAt(LocalDateTime.now());
        room1.setUpdatedAt(LocalDateTime.now());

        Room room2 = new Room();
        room2.setId(2L);
        room2.setHotel(new Hotel());
        room2.setRoomNumber("102");
        room2.setCapacity(3);
        room2.setPricePerNight(BigDecimal.valueOf(150));
        room2.setCreatedAt(LocalDateTime.now());
        room2.setUpdatedAt(LocalDateTime.now());

        List<Room> rooms = List.of(room1, room2);

        List<GetRoomResponse> expectedResponses = List.of(
                new GetRoomResponse(1L, null, "101", 2, BigDecimal.valueOf(100), room1.getCreatedAt(), room1.getUpdatedAt()),
                new GetRoomResponse(2L, null, "102", 3, BigDecimal.valueOf(150), room2.getCreatedAt(), room2.getUpdatedAt())
        );

        try (MockedStatic<RoomMapper> mockedRoomMapper = mockStatic(RoomMapper.class)) {
            when(roomRepository.findAll()).thenReturn(rooms);
            mockedRoomMapper.when(() -> RoomMapper.toListCreateRoomResponse(rooms)).thenReturn(expectedResponses);

            List<GetRoomResponse> responses = roomService.getRoomList();

            assertNotNull(responses);
            assertEquals(2, responses.size());
            assertEquals("101", responses.get(0).roomNumber());
            assertEquals("102", responses.get(1).roomNumber());
            verify(roomRepository).findAll();
        }
    }

    @Test
    void testGetRoomById_whenRoomExists_thenReturnResponse() {
        Room room = new Room();
        room.setId(1L);
        room.setHotel(new Hotel());
        room.setRoomNumber("101");
        room.setCapacity(2);
        room.setPricePerNight(BigDecimal.valueOf(100));
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());

        GetRoomResponse expectedResponse = new GetRoomResponse(
                1L, null, "101", 2, BigDecimal.valueOf(100), room.getCreatedAt(), room.getUpdatedAt()
        );

        try (MockedStatic<RoomMapper> mockedRoomMapper = mockStatic(RoomMapper.class)) {
            when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
            mockedRoomMapper.when(() -> RoomMapper.toGetRoomResponse(room)).thenReturn(expectedResponse);

            GetRoomResponse response = roomService.getRoomById(1L);

            assertNotNull(response);
            assertEquals("101", response.roomNumber());
            verify(roomRepository).findById(1L);
        }
    }

    @Test
    void testUpdateRoom_whenRoomExists_thenUpdateAndReturnResponse() {
        Room existingRoom = new Room();
        existingRoom.setId(1L);

        Room updatedRoom = new Room();
        updatedRoom.setRoomNumber("202");
        updatedRoom.setCapacity(3);
        updatedRoom.setPricePerNight(BigDecimal.valueOf(150.00));

        Room savedRoom = new Room();
        savedRoom.setId(1L);
        savedRoom.setHotel(new Hotel());
        savedRoom.setRoomNumber("202");
        savedRoom.setCapacity(3);
        savedRoom.setPricePerNight(BigDecimal.valueOf(150.00));
        savedRoom.setCreatedAt(LocalDateTime.now());
        savedRoom.setUpdatedAt(LocalDateTime.now());

        GetRoomResponse expectedResponse = new GetRoomResponse(
                savedRoom.getId(),
                null,
                savedRoom.getRoomNumber(),
                savedRoom.getCapacity(),
                savedRoom.getPricePerNight(),
                savedRoom.getCreatedAt(),
                savedRoom.getUpdatedAt()
        );

        try (MockedStatic<RoomMapper> mockedRoomMapper = mockStatic(RoomMapper.class)) {
            when(roomRepository.findById(1L)).thenReturn(Optional.of(existingRoom));
            when(roomRepository.save(existingRoom)).thenReturn(savedRoom);
            mockedRoomMapper.when(() -> RoomMapper.toGetRoomResponse(savedRoom)).thenReturn(expectedResponse);

            GetRoomResponse response = roomService.updateRoom(1L, updatedRoom);

            assertNotNull(response);
            assertEquals("202", response.roomNumber());
            verify(roomRepository).save(existingRoom);
        }
    }

    @Test
    void testDeleteRoom_whenRoomExists_thenDeleteSuccessfully() {
        Room room = new Room();
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        roomService.deleteRoom(1L);

        verify(roomRepository).delete(room);
    }

    @Test
    void testGetRoom_whenRoomNotFound_thenThrowException() {
        when(roomRepository.findById(999L)).thenReturn(Optional.empty());

        BaseException ex = assertThrows(BaseException.class, () -> roomService.getRoomById(999L));

        assertEquals(ErrorCode.ROOM_NOT_FOUND, ex.getCode());
        assertEquals("Room not found.", ex.getMessage());
    }
}