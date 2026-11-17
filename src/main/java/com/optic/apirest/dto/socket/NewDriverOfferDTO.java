// DTO para que las solicitudes de viajes de los clientes sean visibles (o se notifiquen)
// a los conductores cercanos
package com.optic.apirest.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewDriverOfferDTO {

    @JsonProperty("id_client_request")
    private Long idClientRequest;

    @JsonProperty("id_socket")
    private String idSocket;
}
