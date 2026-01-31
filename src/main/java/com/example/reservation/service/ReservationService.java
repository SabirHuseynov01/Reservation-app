package com.example.reservation.service;

import com.example.reservation.dto.ReservationRequestDTO;
import com.example.reservation.dto.ReservationResponseDTO;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);
    ReservationResponseDTO getReservationById(Long id);
    void cancelReservation(Long id);
}
