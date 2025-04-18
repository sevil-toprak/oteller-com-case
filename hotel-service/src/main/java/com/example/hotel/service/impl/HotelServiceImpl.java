package com.example.hotel.service.impl;

import com.example.hotel.entity.Hotel;
import com.example.hotel.exception.BaseException;
import com.example.hotel.exception.constants.ErrorCode;
import com.example.hotel.exception.constants.ErrorMessages;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.model.request.CreateHotelRequest;
import com.example.hotel.model.response.CreateHotelResponse;
import com.example.hotel.model.response.GetHotelResponse;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.service.spec.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Transactional
    @Override
    public CreateHotelResponse createHotel(CreateHotelRequest request) {
        Hotel hotel = HotelMapper.toHotel(request);
        Hotel createdHotel = hotelRepository.save(hotel);
        return HotelMapper.toCreateHotelResponse(createdHotel);
    }

    @Override
    public List<CreateHotelResponse> getHotelList() {
        List<Hotel> hotels = hotelRepository.findAll();
        return HotelMapper.toListCreateHotelResponse(hotels);
    }

    @Override
    public GetHotelResponse getHotelById(Long id) {
        Hotel hotel = getHotel(id);
        return HotelMapper.toGetHotelResponse(hotel);
    }

    @Transactional
    @Override
    public GetHotelResponse updateHotel(Long id, Hotel updatedHotel) {
        Hotel existingHotel = getHotel(id);
        existingHotel.setName(updatedHotel.getName());
        existingHotel.setAddress(updatedHotel.getAddress());
        existingHotel.setStarRating(updatedHotel.getStarRating());
        Hotel hotel = hotelRepository.save(existingHotel);
        return HotelMapper.toGetHotelResponse(hotel);
    }

    @Transactional
    @Override
    public void deleteHotel(Long id) {
        Hotel existingHotel = getHotel(id);
        hotelRepository.delete(existingHotel);
    }

    private Hotel getHotel(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.HOTEL_NOT_FOUND, ErrorMessages.HOTEL_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
