package com.example.hotel.controller;

import com.example.hotel.entity.Room;
import com.example.hotel.model.request.CreateRoomRequest;
import com.example.hotel.model.response.CreateRoomResponse;
import com.example.hotel.model.response.GetRoomResponse;
import com.example.hotel.service.spec.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request) {
        CreateRoomResponse response = roomService.createRoom(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetRoomResponse>> getRoomList() {
        List<GetRoomResponse> rooms = roomService.getRoomList();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRoomResponse> getRoomById(@PathVariable Long id) {
        GetRoomResponse room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetRoomResponse> updateRoom(
            @PathVariable Long id,
            @RequestBody Room updatedRoom
    ) {
        GetRoomResponse updated = roomService.updateRoom(id, updatedRoom);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}