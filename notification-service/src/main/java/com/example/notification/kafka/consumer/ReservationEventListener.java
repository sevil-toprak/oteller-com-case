package com.example.notification.kafka.consumer;

import com.example.notification.kafka.event.ReservationCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReservationEventListener {

    @KafkaListener(topics = "reservation-created", groupId = "notification-group")
    public void handleReservationCreated(ReservationCreatedEvent event) {
        log.info("Received ReservationCreatedEvent: {}", event);
        simulateEmailNotification(event);
    }

    private void simulateEmailNotification(ReservationCreatedEvent event) {
        log.info("Sending e-mail to {} for reservation #{} (Hotel {}, Room {})",
                event.getGuestName(), event.getReservationId(),
                event.getHotelId(), event.getRoomId());
    }
}
