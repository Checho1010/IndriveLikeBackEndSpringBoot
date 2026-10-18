package com.optic.apirest.dto.driver_trip_offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDriverTripOfferResponse {

    private Long id;

    @JsonProperty("id_driver")
    private Long idDriver;

    @JsonProperty("id_client_request")
    private Long idClientRequest;

    @JsonProperty("fare_offered")
    private double fareOffered;

    @JsonProperty("time")
    private double time;

    @JsonProperty("distance")
    private double distance;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
