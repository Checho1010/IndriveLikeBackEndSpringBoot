package com.optic.apirest.repositories;

import com.optic.apirest.models.DriverPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverPositionRepository extends JpaRepository<DriverPosition, Long> {

    @Query(value = """
        SELECT
            id_driver,
            ST_AsText(position) AS position
        FROM
            drivers_position
        WHERE
            id_driver=:id
    """,
            nativeQuery = true
    )
    List<Object[]> findDriverPosition(@Param("id") Long id);

    @Query(value = """
        SELECT
            id_driver,
            ST_AsText(position) AS position,
            ST_Distance_Sphere(position, ST_GeomFromText(CONCAT('POINT(', :lng, ' ', :lat, ')'), 4326)) AS distance
        FROM
            drivers_position
         HAVING
         distance <= 5000
    """, nativeQuery = true)
    List<Object[]> findNearbyDrivers(@Param("lat") double lat, @Param("lng") double lng);
}
