package com.optic.apirest.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DriverPositionDTO {

    @JsonProperty("id_socket")
    private String idSocket; // para identificar la conexión que realizó el usuario
    private int id; // id del conductor
    private double lat;
    private double lng;
}
