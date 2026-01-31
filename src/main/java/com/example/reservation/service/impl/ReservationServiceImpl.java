package com.example.reservation.service.impl;

import com.example.reservation.dto.ReservationRequestDTO;
import com.example.reservation.dto.ReservationResponseDTO;
import com.example.reservation.entity.Reservation;
import com.example.reservation.entity.ReservationStatus;
import com.example.reservation.entity.Resource;
import com.example.reservation.entity.User;
import com.example.reservation.exception.ConflictException;
import com.example.reservation.repository.ReservationRepository;
import com.example.reservation.repository.ResourceRepository;
import com.example.reservation.repository.UserRepository;
import com.example.reservation.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  ResourceRepository resourceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }


    @Override
    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO) {
        Resource resource = resourceRepository.findById(requestDTO.getResourceId())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Reservation> overlaps = reservationRepository.findOverlappingReservations(
                resource.getId(), requestDTO.getStart(), requestDTO.getEnd(), ReservationStatus.ACCEPTED);
        if (!overlaps.isEmpty()) {
            throw new ConflictException("Requested slot overlaps with existing reservation");
        }
        Reservation reservation = Reservation.builder()
                .resource(resource)
                .user(user)
                .start(requestDTO.getStart())
                .end(requestDTO.getEnd())
                .status(ReservationStatus.ACCEPTED)
                .build();

        reservation = reservationRepository.save(reservation);
        return toDto(reservation);
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return toDto(r);
    }

    @Override
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }
    private ReservationResponseDTO toDto (Reservation reservation){
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .resourceId(reservation.getResource().getId())
                .status(reservation.getStatus().name())
                .start(reservation.getStart())
                .end(reservation.getEnd())
                .build();
    }
}
