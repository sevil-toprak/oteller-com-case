package com.example.reservation.model.response;

import com.example.reservation.model.enums.ReservationStatus;

import java.time.LocalDate;

public record GetReservationResponse(Long id,
                                     Long roomId,
                                     Long hotelId,
                                     Long userId,
                                     ReservationStatus status,
                                     LocalDate checkInDate,
                                     LocalDate checkOutDate) {

}
