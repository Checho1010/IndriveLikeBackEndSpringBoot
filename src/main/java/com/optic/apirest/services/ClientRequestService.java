package com.optic.apirest.services;


import com.optic.apirest.dto.client_request.ClientRequestDTO;
import com.optic.apirest.dto.client_request.DistanceMatrixResponse;
import com.optic.apirest.dto.client_request.NearbyClientRequestResponse;
import com.optic.apirest.models.TimeAndDistanceValues;
import com.optic.apirest.repositories.ClientRequestRepository;
import com.optic.apirest.repositories.TimeAndDistanceValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class ClientRequestService {

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    private TimeAndDistanceValuesRepository timeAndDistanceValuesRepository;

    @Autowired
    private ClientRequestRepository clientRequestRepository;

    public Long create(ClientRequestDTO clientRequest) {
        return clientRequestRepository.insertClientRequest(
            clientRequest.getIdClient(),
            clientRequest.getFareOffered(),
            clientRequest.getPickupLat(),
            clientRequest.getPickupLng(),
            clientRequest.getDestinationLat(),
            clientRequest.getDestinationLng(),
            clientRequest.getPickupDescription(),
            clientRequest.getDestinationDescription()
        );
    }

    public DistanceMatrixResponse getTimeAndDistance(double originLat, double originLng, double destinationLat, double destinationLng) {

        TimeAndDistanceValues values = timeAndDistanceValuesRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("Los precios no han sido establecidos")
        );

        String origins = originLat + "," + originLng;
        String destinations = destinationLat + "," + destinationLng;

        URI uri = UriComponentsBuilder
                .fromUriString("https://maps.googleapis.com/maps/api/distancematrix/json")
                .queryParam("origins", origins)
                .queryParam("destinations", destinations)
                .queryParam("units", "metric")
                .queryParam("key", googleApiKey)
                .build(true)
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        // CORRECCIÓN CLAVE: Usamos Map.class en lugar de cualquier clase de Jackson
        ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Error al conectarse al API de Google: " + response.getStatusCode());
        }

        Map<String, Object> body = response.getBody();

        if (!"OK".equals(body.get("status"))) {
            throw new RuntimeException("Respuesta invalida del API de Google: " + body.get("status"));
        }

        // Navegamos de forma segura por las listas y mapas de la respuesta de Google
        List<?> rows = (List<?>) body.get("rows");
        Map<String, Object> firstRow = (Map<String, Object>) rows.get(0);
        List<?> elements = (List<?>) firstRow.get("elements");
        Map<String, Object> firstElement = (Map<String, Object>) elements.get(0);

        // Extraemos Distancia
        Map<String, Object> distanceMap = (Map<String, Object>) firstElement.get("distance");
        String distanceText = (String) distanceMap.get("text");
        double distanceValue = ((Number) distanceMap.get("value")).doubleValue();

        // Extraemos Duración
        Map<String, Object> durationMap = (Map<String, Object>) firstElement.get("duration");
        String durationText = (String) durationMap.get("text");
        double durationValue = ((Number) durationMap.get("value")).doubleValue();

        // Creamos variables para obtener los kms y los minutos
        double km = distanceValue / 1000;
        double minutes = durationValue / 60;
        // Ahora calculamos el valor sugerido del viaje
        double recommendedValue = values.getKmValue() * km + values.getMinValue() * minutes;



        // Construimos el DTO final
        DistanceMatrixResponse responseDTO = new DistanceMatrixResponse();

        List<?> originAddresses = (List<?>) body.get("origin_addresses");
        List<?> destinationAddresses = (List<?>) body.get("destination_addresses");

        responseDTO.setOriginAddresses((String) originAddresses.get(0));
        responseDTO.setDestinationAddresses((String) destinationAddresses.get(0));

        DistanceMatrixResponse.Duration duration = new DistanceMatrixResponse.Duration();
        duration.setText(durationText);
        duration.setValue(durationValue);
        responseDTO.setDuration(duration); // ¡Guardamos en el DTO!

        DistanceMatrixResponse.Distance distance = new DistanceMatrixResponse.Distance();
        distance.setText(distanceText);
        distance.setValue(distanceValue);
        responseDTO.setDistance(distance); // ¡Guardamos en el DTO!

        //responseDTO.setRecommendedValue(String.format("%.2f", recommendedValue));// %.2f formatea a 2 decimales
        responseDTO.setRecommendedValue(recommendedValue);

        return responseDTO;
    }

    public List<NearbyClientRequestResponse> findNearbyClientRequest(double driverLat, double driverLng) {
        //return clientRequestRepository.findNearbyClientRequest(driverLat, driverLng);
        return null;
    }
}
