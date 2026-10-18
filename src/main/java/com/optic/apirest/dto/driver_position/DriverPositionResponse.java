package com.optic.apirest.dto.driver_position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverPositionResponse {

    @JsonProperty("id_driver")
    private Long idDriver;
    private double lat;
    private double lng;
}
