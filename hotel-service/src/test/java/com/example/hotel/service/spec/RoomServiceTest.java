package com.example.hotel.service.spec;

import com.example.hotel.entity.Room;
import com.example.hotel.model.request.CreateRoomRequest;
import com.example.hotel.model.response.CreateRoomResponse;
import com.example.hotel.model.response.GetRoomResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RoomServiceTest {

    private final RoomService roomService = new RoomService() {
        @Override
        public CreateRoomResponse createRoom(CreateRoomRequest request) {
            return null;
        }

        @Override
        public List<GetRoomResponse> getRoomList() {
            return null;
        }

        @Override
        public GetRoomResponse getRoomById(Long id) {
            return null;
        }

        @Override
        public GetRoomResponse updateRoom(Long id, Room updatedData) {
            return null;
        }

        @Override
        public void deleteRoom(Long id) {
        }
    };

    @Test
    void testInterfaceMethodsExist() {
        assertDoesNotThrow(() -> roomService.createRoom(new CreateRoomRequest(1L, "101", 2, BigDecimal.valueOf(100))));
        assertDoesNotThrow(() -> roomService.getRoomList());
        assertDoesNotThrow(() -> roomService.getRoomById(1L));
        assertDoesNotThrow(() -> roomService.updateRoom(1L, new Room()));
        assertDoesNotThrow(() -> roomService.deleteRoom(1L));
    }
}
