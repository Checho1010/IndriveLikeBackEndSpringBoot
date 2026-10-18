package com.optic.apirest.dto.driver_position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DriverPositionRequest {

    @JsonProperty("id_driver")
    private Long idDriver;
    private double lat;
    private double lng;
}
