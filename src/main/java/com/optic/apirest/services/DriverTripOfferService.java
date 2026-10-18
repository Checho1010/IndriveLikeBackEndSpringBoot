package com.optic.apirest.services;

import com.optic.apirest.dto.driver_trip_offer.DriverTripOfferRequest;
import com.optic.apirest.dto.driver_trip_offer.CreateDriverTripOfferResponse;
import com.optic.apirest.dto.driver_trip_offer.DriverTripOfferResponse;
import com.optic.apirest.models.User;
import com.optic.apirest.repositories.ClientRequestRepository;
import com.optic.apirest.repositories.DriverTripOfferRepository;
import com.optic.apirest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverTripOfferService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRequestRepository clientRequestRepository;

    @Autowired
    private DriverTripOfferRepository driverTripOfferRepository;

    @Transactional
    public CreateDriverTripOfferResponse create(DriverTripOfferRequest request) {
        User driver = userRepository.findById(request.getIdDriver()).orElseThrow(
                () -> new RuntimeException("El conductor no existe")
        );

        boolean existsClientRequest = clientRequestRepository.findById(request.getIdClientRequest());

        if(!existsClientRequest) {
            throw new RuntimeException("La solicitud de viaje no existe");
        }

        Long id = driverTripOfferRepository.insertDriverTripOffer(request);
        CreateDriverTripOfferResponse response = new CreateDriverTripOfferResponse();
        response.setId(id);
        response.setIdDriver(request.getIdDriver());
        response.setIdClientRequest(request.getIdClientRequest());
        response.setFareOffered(request.getFareOffered());
        response.setDistance(request.getDistance());
        response.setTime(request.getTime());
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());
        return response;
    }

    @Transactional
    public List<DriverTripOfferResponse> findByClientRequest(Long idClientRequest) {
        return driverTripOfferRepository.findByClientRequest(idClientRequest);
    }
}
