package com.optic.apirest.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "time_and_distance_values")
@Data
public class TimeAndDistanceValues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="km_value")
    private double kmValue;

    @Column(name ="min_value")
    private double minValue;

}
