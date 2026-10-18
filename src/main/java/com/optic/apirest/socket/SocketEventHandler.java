package com.optic.apirest.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.optic.apirest.dto.socket.DriverPositionDTO;
import com.optic.apirest.dto.socket.MessageDTO;
import com.optic.apirest.dto.socket.SocketIdResponse;
import org.springframework.stereotype.Component;

@Component
public class SocketEventHandler {

    public SocketEventHandler(SocketIOServer server) {
        server.addConnectListener(client -> System.out.println("Cliente Conectado: " + client.getSessionId()));

        server.addDisconnectListener(client -> {
            System.out.println("Cliente Desconectado: " + client.getSessionId());
            SocketIdResponse response = new SocketIdResponse();
            response.setIdSocket(client.getSessionId().toString());
            server.getBroadcastOperations().sendEvent("driver_disconnected", response);
        });

        // Para agregar un evento personalizado usamos addEventListener
        server.addEventListener("message", MessageDTO.class, (client, data, ackSender) -> {
            System.out.println("Mensaje recibido: " + data.getNewMessage());
            server.getBroadcastOperations().sendEvent("new_message_response", "Hola desde el servidor " + data.getNewMessage());
        });

        server.addEventListener("change_driver_position", DriverPositionDTO.class, (client, data, ackSender) -> {

            DriverPositionDTO position = new DriverPositionDTO();
            position.setIdSocket(client.getSessionId().toString());
            position.setId(data.getId());
            position.setLat(data.getLat());
            position.setLng(data.getLng());

            server.getBroadcastOperations().sendEvent("new_driver_position", position);
        });
    }
}
