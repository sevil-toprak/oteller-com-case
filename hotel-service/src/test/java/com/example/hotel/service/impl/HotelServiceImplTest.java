package com.example.hotel.service.impl;

import com.example.hotel.entity.Hotel;
import com.example.hotel.exception.BaseException;
import com.example.hotel.exception.constants.ErrorCode;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.model.request.CreateHotelRequest;
import com.example.hotel.model.response.CreateHotelResponse;
import com.example.hotel.model.response.GetHotelResponse;
import com.example.hotel.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    void testCreateHotel_whenValidRequest_thenReturnResponse() {
        CreateHotelRequest request = new CreateHotelRequest("Test Hotel", "Test Address", 4, List.of());
        Hotel hotelEntity = new Hotel();
        Hotel savedHotel = new Hotel();
        CreateHotelResponse expectedResponse = new CreateHotelResponse(1L, "Test Hotel", "Test Address", 4, LocalDateTime.now(), LocalDateTime.now());

        try (MockedStatic<HotelMapper> mockedMapper = mockStatic(HotelMapper.class)) {
            mockedMapper.when(() -> HotelMapper.toHotel(request)).thenReturn(hotelEntity);
            when(hotelRepository.save(any())).thenReturn(savedHotel);
            mockedMapper.when(() -> HotelMapper.toCreateHotelResponse(savedHotel)).thenReturn(expectedResponse);

            CreateHotelResponse response = hotelService.createHotel(request);

            assertNotNull(response);
            assertEquals("Test Hotel", response.name());
            verify(hotelRepository, times(1)).save(hotelEntity);
        }
    }

    @Test
    void testGetHotelList_whenHotelsExist_thenReturnList() {
        List<Hotel> hotelEntities = List.of(new Hotel(), new Hotel());
        List<CreateHotelResponse> expectedResponses = List.of(
                new CreateHotelResponse(1L, "Hotel 1", "Address 1", 3, LocalDateTime.now(), LocalDateTime.now()),
                new CreateHotelResponse(2L, "Hotel 2", "Address 2", 4, LocalDateTime.now(), LocalDateTime.now())
        );

        try (MockedStatic<HotelMapper> mockedMapper = mockStatic(HotelMapper.class)) {
            when(hotelRepository.findAll()).thenReturn(hotelEntities);
            mockedMapper.when(() -> HotelMapper.toListCreateHotelResponse(hotelEntities))
                    .thenReturn(expectedResponses);

            List<CreateHotelResponse> responses = hotelService.getHotelList();

            assertNotNull(responses);
            assertEquals(2, responses.size());
            verify(hotelRepository, times(1)).findAll();
        }
    }

    @Test
    void testGetHotelById_whenHotelExists_thenReturnHotel() {
        Hotel hotelEntity = new Hotel();
        hotelEntity.setId(1L);
        hotelEntity.setName("Test Hotel");
        hotelEntity.setAddress("Test Address");
        hotelEntity.setStarRating(5);
        hotelEntity.setCreatedAt(LocalDateTime.now());
        hotelEntity.setUpdatedAt(LocalDateTime.now());
        hotelEntity.setVersion(1L);

        GetHotelResponse expectedResponse = new GetHotelResponse(
                hotelEntity.getId(),
                hotelEntity.getName(),
                hotelEntity.getAddress(),
                hotelEntity.getStarRating(),
                hotelEntity.getCreatedAt(),
                hotelEntity.getUpdatedAt(),
                hotelEntity.getVersion()
        );

        try (MockedStatic<HotelMapper> mockedMapper = mockStatic(HotelMapper.class)) {
            when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotelEntity));
            mockedMapper.when(() -> HotelMapper.toGetHotelResponse(hotelEntity)).thenReturn(expectedResponse);

            GetHotelResponse response = hotelService.getHotelById(1L);

            assertNotNull(response);
            assertEquals("Test Hotel", response.name());
            verify(hotelRepository, times(1)).findById(1L);
        }
    }

    @Test
    void testUpdateHotel_whenHotelExists_thenReturnUpdatedHotel() {
        Hotel existingHotel = new Hotel();
        existingHotel.setId(1L);
        existingHotel.setName("Old Name");
        existingHotel.setAddress("Old Address");
        existingHotel.setStarRating(3);
        existingHotel.setCreatedAt(LocalDateTime.now().minusDays(1));
        existingHotel.setUpdatedAt(LocalDateTime.now());
        existingHotel.setVersion(1L);

        Hotel updatedHotel = new Hotel();
        updatedHotel.setName("New Name");
        updatedHotel.setAddress("New Address");
        updatedHotel.setStarRating(5);

        Hotel savedHotel = new Hotel();
        savedHotel.setId(1L);
        savedHotel.setName("New Name");
        savedHotel.setAddress("New Address");
        savedHotel.setStarRating(5);
        savedHotel.setCreatedAt(existingHotel.getCreatedAt());
        savedHotel.setUpdatedAt(LocalDateTime.now());
        savedHotel.setVersion(2L);

        GetHotelResponse expectedResponse = new GetHotelResponse(
                savedHotel.getId(),
                savedHotel.getName(),
                savedHotel.getAddress(),
                savedHotel.getStarRating(),
                savedHotel.getCreatedAt(),
                savedHotel.getUpdatedAt(),
                savedHotel.getVersion()
        );

        try (MockedStatic<HotelMapper> mockedMapper = mockStatic(HotelMapper.class)) {
            when(hotelRepository.findById(1L)).thenReturn(Optional.of(existingHotel));
            when(hotelRepository.save(existingHotel)).thenReturn(savedHotel);
            mockedMapper.when(() -> HotelMapper.toGetHotelResponse(savedHotel)).thenReturn(expectedResponse);

            GetHotelResponse response = hotelService.updateHotel(1L, updatedHotel);

            assertNotNull(response);
            assertEquals("New Name", response.name());
            verify(hotelRepository).save(existingHotel);
        }
    }


    @Test
    void testDeleteHotel_whenHotelExists_thenDeletedSuccessfully() {
        Hotel hotel = new Hotel();
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        hotelService.deleteHotel(1L);

        verify(hotelRepository).delete(hotel);
    }

    @Test
    void testGetHotel_whenHotelNotFound_thenThrowException() {
        when(hotelRepository.findById(999L)).thenReturn(Optional.empty());

        try (MockedStatic<HotelMapper> mockedMapper = mockStatic(HotelMapper.class)) {
            BaseException exception = assertThrows(BaseException.class, () -> hotelService.getHotelById(999L));

            assertEquals(ErrorCode.HOTEL_NOT_FOUND, exception.getCode());
            assertEquals("Hotel not found.", exception.getMessage());
        }
    }
}
