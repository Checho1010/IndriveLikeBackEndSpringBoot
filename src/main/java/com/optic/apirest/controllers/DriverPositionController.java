package com.optic.apirest.controllers;

import com.optic.apirest.dto.driver_position.DriverPositionRequest;
import com.optic.apirest.dto.driver_position.DriverPositionResponse;
import com.optic.apirest.dto.driver_position.NearbyDriverPositionResponse;
import com.optic.apirest.models.DriverPosition;
import com.optic.apirest.services.DriverPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drivers-position")
public class DriverPositionController {

    @Autowired
    private DriverPositionService driverPositionService;

    @PostMapping() // PostMapping porque vamos a crear la informacion
    public ResponseEntity<?> create (@RequestBody DriverPositionRequest request) {
        try {
            DriverPositionResponse response = driverPositionService.create(request);
            return ResponseEntity.ok(true); // el true indica que se creo la informacion correctamente
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.NOT_FOUND.value()
            ));
        }
    }

    @GetMapping(value = "/{idDriver}") // PostMapping porque vamos a crear la informacion
    public ResponseEntity<?> getDriverPosition (@PathVariable Long idDriver) {
        try {
            DriverPositionResponse response = driverPositionService.getDriverPosition(idDriver);
            return ResponseEntity.ok(response); // el true indica que se creo la informacion correctamente
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.NOT_FOUND.value()
            ));
        }
    }

    @GetMapping(value = "/nearby/{lat}/{lng}")
    public ResponseEntity<?> getNearbyDrivers (@PathVariable double lat, @PathVariable double lng) {
        try {
            List<NearbyDriverPositionResponse> response = driverPositionService.getNearbyDrivers(lat,lng);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.NOT_FOUND.value()
            ));
        }
    }

    @DeleteMapping(value = "/{idDriver}")
    public ResponseEntity<?> delete (@PathVariable long idDriver) {
        try {
            driverPositionService.delete(idDriver);
            return ResponseEntity.ok(true);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.NOT_FOUND.value()
            ));
        }
    }
}
