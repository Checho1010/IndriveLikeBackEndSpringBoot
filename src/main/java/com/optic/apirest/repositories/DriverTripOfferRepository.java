package com.optic.apirest.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.optic.apirest.config.APIConfig;
import com.optic.apirest.dto.driver_trip_offer.DriverTripOfferRequest;
import com.optic.apirest.dto.driver_trip_offer.DriverTripOfferResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Repository
public class DriverTripOfferRepository  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long insertDriverTripOffer(DriverTripOfferRequest request) {
        String sql = """
            INSERT INTO driver_trip_offers(
                id_driver,
                id_client_request,
                fare_offered,
                time,
                distance,
                created_at,
                updated_at
            ) VALUES (?, ?, ?, ?, ?, NOW(), NOW())
        """;

        jdbcTemplate.update(sql,
            request.getIdDriver(),
            request.getIdClientRequest(),
            request.getFareOffered(),
            request.getTime(),
            request.getDistance()
        );
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public List<DriverTripOfferResponse> findByClientRequest(Long idClientRequest) {
        String sql = """
            SELECT 
                DTO.id,
                DTO.id_driver,
                DTO.id_client_request,
                DTO.fare_offered,
                DTO.time,
                DTO.distance,
                DTO.created_at,
                DTO.updated_at,
                JSON_OBJECT(
                    'name', U.name,
                    'lastName', U.last_name,
                    'phone', U.phone,
                    'image', U.image
                ) AS driver
            FROM
                driver_trip_offers AS DTO
            INNER JOIN
                users AS U
            ON
                U.id = DTO.id_driver
            WHERE
                DTO.id_client_request = ?
        """;
        return jdbcTemplate.query(sql, new Object[] {idClientRequest}, (result, rowNumber) -> {
            ObjectMapper mapper = new ObjectMapper();
//            try {
                DriverTripOfferResponse.DriverInfoDTO driverInfo = mapper.readValue(result.getString("driver"), DriverTripOfferResponse.DriverInfoDTO.class);
                if (driverInfo.getImage() != null) {
                    driverInfo.setImage(APIConfig.BASE_URL + driverInfo.getImage());
                }

                DriverTripOfferResponse response = new DriverTripOfferResponse();
                response.setId(result.getLong("id"));
                response.setIdClientRequest(result.getLong("id_client_request"));
                response.setIdDriver(result.getLong("id_driver"));
                response.setFareOffered(result.getDouble("fare_offered"));
                response.setTime(result.getDouble("time"));
                response.setDistance(result.getDouble("distance"));
                response.setDriver(driverInfo);
                response.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());
                response.setUpdatedAt(result.getTimestamp("updated_at").toLocalDateTime());

                return response;
//            } catch (JsonProcessingException e){
//                throw new RuntimeException("Error deserializando informacion", e);
//            }
        });
    }
}
