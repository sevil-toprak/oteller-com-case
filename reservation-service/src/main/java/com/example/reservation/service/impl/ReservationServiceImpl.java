package com.example.reservation.service.impl;

import com.example.reservation.entity.Reservation;
import com.example.reservation.exception.BaseException;
import com.example.reservation.exception.constants.ErrorCode;
import com.example.reservation.exception.constants.ErrorMessages;
import com.example.reservation.kafka.event.ReservationCreatedEvent;
import com.example.reservation.mapper.ReservationMapper;
import com.example.reservation.model.enums.ReservationStatus;
import com.example.reservation.model.request.CreateReservationRequest;
import com.example.reservation.model.response.GetReservationResponse;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.service.spec.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final String TOPIC_NAME = "reservation-created";
    private final ReservationRepository reservationRepository;
    private final KafkaTemplate<String, ReservationCreatedEvent> kafkaTemplate;

    @Override
    @Transactional
    public Reservation createReservation(CreateReservationRequest request) {
        if (isExistsOverlap(request)) {
            throw new BaseException(ErrorCode.ROOM_ALREADY_RESERVED, ErrorMessages.ROOM_ALREADY_RESERVED, HttpStatus.CONFLICT);
        }

        Reservation reservation = ReservationMapper.toReservationForCreate(request);
        Reservation saved = reservationRepository.save(reservation);

        ReservationCreatedEvent event = buildReservationCreatedEvent(saved);
        kafkaTemplate.send(TOPIC_NAME, event);

        return saved;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public GetReservationResponse getReservationById(Long id, Long userId) {
        Optional<Reservation> optionalReservation = reservationRepository.findByIdAndUserId(id, userId);
        if (optionalReservation.isEmpty()) {
            throw new BaseException(ErrorCode.RESERVATION_NOT_FOUND, ErrorMessages.RESERVATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Reservation reservation = optionalReservation.get();
        return ReservationMapper.toGetReservationResponse(reservation);
    }

    @Override
    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.RESERVATION_NOT_FOUND, ErrorMessages.RESERVATION_NOT_FOUND, HttpStatus.NOT_FOUND));
        reservationRepository.delete(reservation);
    }

    private boolean isExistsOverlap(CreateReservationRequest request) {
        return reservationRepository.existsOverlap(request, ReservationStatus.CANCELLED);
    }

    private ReservationCreatedEvent buildReservationCreatedEvent(Reservation saved) {
        return ReservationCreatedEvent.builder()
                .reservationId(saved.getId())
                .hotelId(saved.getHotelId())
                .roomId(saved.getRoomId())
                .guestName(saved.getGuestName())
                .checkInDate(saved.getCheckInDate())
                .checkOutDate(saved.getCheckOutDate())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
