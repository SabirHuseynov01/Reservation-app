package com.example.reservation.controller;


import com.example.reservation.dto.ReservationRequestDTO;
import com.example.reservation.dto.ReservationResponseDTO;
import com.example.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@RequestBody @Valid ReservationRequestDTO req) {
        ReservationResponseDTO resp = reservationService.createReservation(req);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
