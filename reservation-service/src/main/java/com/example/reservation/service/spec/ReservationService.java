package com.example.reservation.service.spec;

import com.example.reservation.entity.Reservation;
import com.example.reservation.model.request.CreateReservationRequest;
import com.example.reservation.model.response.GetReservationResponse;

import java.util.List;

public interface ReservationService {

    Reservation createReservation(CreateReservationRequest request);

    List<Reservation> getAllReservations();

    GetReservationResponse getReservationById(Long id, Long userId);

    void deleteReservation(Long id);
}
