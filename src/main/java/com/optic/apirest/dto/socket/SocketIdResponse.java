package com.optic.apirest.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SocketIdResponse {

    @JsonProperty("id_socket")
    private String idSocket;

}
