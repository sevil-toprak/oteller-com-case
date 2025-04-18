package com.example.reservation.controller;

import com.example.reservation.entity.Reservation;
import com.example.reservation.model.request.CreateReservationRequest;
import com.example.reservation.model.response.GetReservationResponse;
import com.example.reservation.service.spec.ReservationService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest request) {
        Reservation response = reservationService.createReservation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> response = reservationService.getAllReservations();
        return ResponseEntity.ok(response);
    }

    // It is not a correct way for getting userId. We need to general way get userId from principal.
    // Additionally, we can get some other information like trace_id, ip, etc.
    // Map<String, Object> claims = authentication.getToken().getClaims();
    @GetMapping("/{id}")
    public ResponseEntity<GetReservationResponse> getReservationById(@PathVariable Long id, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getPrincipal().toString());
        GetReservationResponse response = reservationService.getReservationById(id, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}