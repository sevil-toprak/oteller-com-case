package com.example.reservation.repository;

import com.example.reservation.entity.Reservation;
import com.example.reservation.model.enums.ReservationStatus;
import com.example.reservation.model.request.CreateReservationRequest;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
            FROM   Reservation r
            WHERE  r.hotelId      = :#{#reservation.hotelId}
              AND  r.roomId       = :#{#reservation.roomId}
              AND  r.status        <> :status
              AND  r.checkInDate   <  :#{#reservation.checkOutDate}
              AND  r.checkOutDate  >  :#{#reservation.checkInDate}
            """)
    boolean existsOverlap(@Param("reservation") CreateReservationRequest reservation, ReservationStatus status);

    Optional<Reservation> findByIdAndUserId(Long id, Long userId);
}