package com.optic.apirest.dto.client_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientRequestDTO {

    @JsonProperty("id_client")
    private Long idClient;

    @JsonProperty("fare_offered")
    private double fareOffered;

    @JsonProperty("pickup_lat")
    private double pickupLat;

    @JsonProperty("pickup_lng")
    private double pickupLng;

    @JsonProperty("destination_lat")
    private double destinationLat;

    @JsonProperty("destination_lng")
    private double destinationLng;

    @JsonProperty("pickup_description")
    private String pickupDescription;

    @JsonProperty("destination_description")
    private String destinationDescription;
}
