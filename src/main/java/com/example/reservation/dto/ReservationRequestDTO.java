package com.example.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {
    @NotNull
    private Long resourceId;

    @NotNull
    private Long userId;

    @NotNull
    @Future
    private OffsetDateTime start;

    @NotNull
    @Future
    private OffsetDateTime end;
}
