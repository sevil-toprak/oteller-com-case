package com.example.reservation.model.request;

import java.time.LocalDate;

public record CreateReservationRequest(Long roomId,
                                       Long hotelId,
                                       Long userId,
                                       LocalDate checkInDate,
                                       LocalDate checkOutDate) {

}
