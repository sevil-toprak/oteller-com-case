package com.example.reservation.mapper;


import com.example.reservation.entity.Reservation;
import com.example.reservation.model.enums.ReservationStatus;
import com.example.reservation.model.request.CreateReservationRequest;
import com.example.reservation.model.response.GetReservationResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReservationMapper {

    // It is a basic sample class. We can use MapStruct, ModelMapper, or other general Mapper libraries.
    public static Reservation toReservationForCreate(CreateReservationRequest request) {
        return new Reservation(
                request.hotelId(),
                request.roomId(),
                request.userId(),
                "",
                request.checkInDate(),
                request.checkOutDate(),
                ReservationStatus.INIT
        );
    }

    public static GetReservationResponse toGetReservationResponse(Reservation reservation) {
        return new GetReservationResponse(
                reservation.getId(),
                reservation.getRoomId(),
                reservation.getHotelId(),
                reservation.getUserId(),
                reservation.getStatus(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        );
    }
}