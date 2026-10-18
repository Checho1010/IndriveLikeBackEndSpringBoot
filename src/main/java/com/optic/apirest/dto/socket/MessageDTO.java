package com.optic.apirest.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageDTO {

    // Este campo es la informacion que vamos a recibir del cliente
    @JsonProperty("new_message")
    private String newMessage;
}
