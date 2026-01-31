package com.example.reservation.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {
    private Long id;
    private Long userId;
    private Long resourceId;
    private String status;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
