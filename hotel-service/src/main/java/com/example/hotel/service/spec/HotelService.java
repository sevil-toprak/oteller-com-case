package com.example.hotel.service.spec;

import com.example.hotel.entity.Hotel;
import com.example.hotel.model.request.CreateHotelRequest;
import com.example.hotel.model.response.CreateHotelResponse;
import com.example.hotel.model.response.GetHotelResponse;

import java.util.List;

public interface HotelService {

    CreateHotelResponse createHotel(CreateHotelRequest request);

    List<CreateHotelResponse> getHotelList();

    GetHotelResponse getHotelById(Long id);

    GetHotelResponse updateHotel(Long id, Hotel updatedHotel);

    void deleteHotel(Long id);

}
