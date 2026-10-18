package com.optic.apirest.dto.driver_position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearbyDriverPositionResponse {

    @JsonProperty("id_driver")
    private Long idDriver;
    private Position position;
    private double distance;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private double x; // lng
        private double y; // lat
    }
}
