package com.optic.apirest.controllers;

import com.optic.apirest.dto.driver_trip_offer.DriverTripOfferRequest;
import com.optic.apirest.dto.driver_trip_offer.CreateDriverTripOfferResponse;
import com.optic.apirest.dto.driver_trip_offer.DriverTripOfferResponse;
import com.optic.apirest.services.DriverTripOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/driver-trip-offers")
public class DriverTripOfferController {

    @Autowired
    private DriverTripOfferService driverTripOfferService;

    @PostMapping()
    public ResponseEntity<?> create (@RequestBody DriverTripOfferRequest request) {
        try {
            CreateDriverTripOfferResponse response = driverTripOfferService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.BAD_REQUEST.value()
            ));
        }
    }

    @GetMapping("/findByClientRequest/{idClientRequest}")
    public ResponseEntity<?> findByClientRequest (@PathVariable Long idClientRequest) {
        try {
            List<DriverTripOfferResponse> response = driverTripOfferService.findByClientRequest(idClientRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.BAD_REQUEST.value()
            ));
        }
    }

}
