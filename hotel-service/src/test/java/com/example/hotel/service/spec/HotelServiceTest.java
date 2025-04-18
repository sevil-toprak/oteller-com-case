package com.example.hotel.service.spec;

import com.example.hotel.entity.Hotel;
import com.example.hotel.model.request.CreateHotelRequest;
import com.example.hotel.model.response.CreateHotelResponse;
import com.example.hotel.model.response.GetHotelResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HotelServiceTest {

    private final HotelService hotelService = new HotelService() {
        @Override
        public CreateHotelResponse createHotel(CreateHotelRequest request) {
            return null;
        }

        @Override
        public List<CreateHotelResponse> getHotelList() {
            return null;
        }

        @Override
        public GetHotelResponse getHotelById(Long id) {
            return null;
        }

        @Override
        public GetHotelResponse updateHotel(Long id, Hotel updatedHotel) {
            return null;
        }

        @Override
        public void deleteHotel(Long id) {
        }
    };

    @Test
    void testInterfaceMethodsExist() {
        assertDoesNotThrow(() -> hotelService.createHotel(new CreateHotelRequest("name", "address", 3, List.of())));
        assertDoesNotThrow(() -> hotelService.getHotelList());
        assertDoesNotThrow(() -> hotelService.getHotelById(1L));
        assertDoesNotThrow(() -> hotelService.updateHotel(1L, new Hotel()));
        assertDoesNotThrow(() -> hotelService.deleteHotel(1L));
    }
}
