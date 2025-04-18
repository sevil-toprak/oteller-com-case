package com.example.hotel.controller;

import com.example.hotel.entity.Hotel;
import com.example.hotel.model.request.CreateHotelRequest;
import com.example.hotel.model.response.CreateHotelResponse;
import com.example.hotel.model.response.GetHotelResponse;
import com.example.hotel.service.spec.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<CreateHotelResponse> createHotel(@RequestBody CreateHotelRequest request) {
        CreateHotelResponse response = hotelService.createHotel(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CreateHotelResponse>> getHotelList() {
        List<CreateHotelResponse> hotels = hotelService.getHotelList();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetHotelResponse> getHotelById(@PathVariable Long id) {
        GetHotelResponse response = hotelService.getHotelById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetHotelResponse> updateHotel(
            @PathVariable Long id,
            @RequestBody Hotel updatedHotel
    ) {
        GetHotelResponse response = hotelService.updateHotel(id, updatedHotel);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}