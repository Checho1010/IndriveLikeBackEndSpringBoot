package com.optic.apirest.repositories;

import com.optic.apirest.models.TimeAndDistanceValues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeAndDistanceValuesRepository extends JpaRepository<TimeAndDistanceValues, Long> {
}
