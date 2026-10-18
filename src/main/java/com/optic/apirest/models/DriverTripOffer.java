package com.optic.apirest.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "driver_trip_offers")
@Data
public class DriverTripOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_driver", nullable = false)
    private User driver;

    @ManyToOne
    @JoinColumn(name = "id_client_request", nullable = false)
    private ClientRequest clientRequest;

    @Column(name = "fare_offered", nullable = false)
    private double fareOffered;

    @Column(name = "time", nullable = false)
    private double time;

    @Column(name = "distance", nullable = false)
    private double distance;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
