package com.optic.apirest.controllers;

import com.optic.apirest.dto.client_request.ClientRequestDTO;
import com.optic.apirest.dto.client_request.DistanceMatrixResponse;
import com.optic.apirest.dto.client_request.NearbyClientRequestResponse;
import com.optic.apirest.services.ClientRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("client-requests")
public class ClientRequestController {

    @Autowired
    private ClientRequestService clientRequestService;

    @GetMapping(value = "/{originLat}/{originLng}/{destinationLat}/{destinationLng}")
    public ResponseEntity<?> getTimeAndDistance (
            @PathVariable double originLat,
            @PathVariable double originLng,
            @PathVariable double destinationLat,
            @PathVariable double destinationLng
    ) {
        try {
            DistanceMatrixResponse response = clientRequestService.getTimeAndDistance(
                    originLat,
                    originLng,
                    destinationLat,
                    destinationLng
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.BAD_REQUEST.value()
            ));
        }
    }

    @GetMapping(value = "/findNearbyClientRequest/{driverLat}/{driverLng}")
    public ResponseEntity<?> findNearbyClientRequest (
            @PathVariable double driverLat,
            @PathVariable double driverLng
    ) {
        try {
            List<NearbyClientRequestResponse> response = clientRequestService.findNearbyClientRequest(
                    driverLat,
                    driverLng
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.BAD_REQUEST.value()
            ));
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create (@RequestBody ClientRequestDTO request) {
        try {
            Long id = clientRequestService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "message", e.getMessage(),
                    "statusCode", HttpStatus.BAD_REQUEST.value()
            ));
        }
    }
}
