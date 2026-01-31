package com.example.reservation.repository;

import com.example.reservation.entity.Reservation;
import com.example.reservation.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findOverlappingReservations(
            @Param("resourceId") Long resourceId,
            @Param("start") OffsetDateTime start,
            @Param("end") OffsetDateTime end,
            @Param("status") ReservationStatus status);
}
