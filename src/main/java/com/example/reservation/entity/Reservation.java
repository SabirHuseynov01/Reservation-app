package com.example.reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "reservations",
        indexes = {@Index(name = "idx_res_resource_start_end", columnList = "resource_id,start_time,end_time")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime start;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime end;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "created_at",updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @Version
    private Long version;


}
