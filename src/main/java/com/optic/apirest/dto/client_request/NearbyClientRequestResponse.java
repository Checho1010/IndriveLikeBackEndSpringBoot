package com.optic.apirest.dto.client_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearbyClientRequestResponse {

    private Long id;

    @JsonProperty("id_client")
    private Long idClient;

    @JsonProperty("pickup_description")
    private String pickupDescription;

    @JsonProperty("destination_description")
    private String destinationDescription;

    @JsonProperty("fare_offered")
    private double fareOffered;

    private double distance;

    @JsonProperty("time_difference")
    private int timeDifference;

    private String status;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private ClientInfoDTO client;

    @JsonProperty("pickup_position")
    private CoordinatesDTO pickupPosition;

    @JsonProperty("destination_position")
    private CoordinatesDTO destinationPosition;

    @JsonProperty("google_distance_matrix")
    private GoogleDistanceMatrixDTO googleDistanceMatrix;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientInfoDTO {
        private String name;
        @JsonProperty("last_name")
        private String lastName;
        private String phone;
        private String image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoordinatesDTO {
        private double x;
        private double y;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GoogleDistanceMatrixDTO {
        private String status;
        private ValueText distance;
        private ValueText duration;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ValueText {
            private String text;
            private double value;
        }
    }
}
