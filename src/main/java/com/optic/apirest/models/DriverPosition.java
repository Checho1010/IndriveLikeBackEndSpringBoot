package com.optic.apirest.models;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "drivers_position")
@Data
public class DriverPosition {

    @Id
    @Column(name = "id_driver")
    private Long idDriver;

    @OneToOne
    // @MapsId representa que el campo id_driver (idDriver) es al mismo tiempo una
    // PK (de la tabla drivers_position) y una FK (hacia la tabla users)
    @MapsId
    @JoinColumn(name = "id_driver")
    private User user;

    @Column(columnDefinition = "POINT")
    private Point position;
}
