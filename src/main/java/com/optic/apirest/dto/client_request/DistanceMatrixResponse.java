package com.optic.apirest.dto.client_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceMatrixResponse {

    @JsonProperty("recommended_value")
    private double recommendedValue;

    @JsonProperty("destination_addresses")
    private String destinationAddresses;

    @JsonProperty("origin_addresses")
    private String originAddresses;
    private Distance distance;
    private Duration duration;

    @Data
    public static class Distance {
        private String text;
        private double value;
    }

    @Data
    public static class Duration {
        private String text;
        private double value;
    }
}
