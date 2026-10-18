package com.optic.apirest.models;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "client_requests")
public class ClientRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_driver_assigned", nullable = true)
    private User driverAssigned;

    @Column(name="fare_offered", nullable = false)
    private double fareOffered;

    @Column(name="pickup_description", nullable = false, length = 255)
    private String pickupDescription;

    @Column(name="destination_description", nullable = false, length = 255)
    private String destinationDescription;

    @Column(name="fare_assigned", nullable = true)
    private double fareAssigned;

    @Column(name="client_rating", nullable = true)
    private double clientRating;

    @Column(name="driver_rating", nullable = true)
    private double driverRating;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    private Status status = Status.CREATED;

    @Column(name = "pickup_position", columnDefinition = "POINT SRID 4326")
    private Point pickupPosition;

    @Column(name = "destination_position", columnDefinition = "POINT SRID 4326")
    private Point destinationPosition;

    public enum Status {
        CREATED,
        ACCEPTED,
        ON_THE_WAY,
        ARRIVED,
        TRAVELLING,
        FINISHED,
        CANCELLED
    }

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
