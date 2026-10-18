package com.optic.apirest.services;

import com.optic.apirest.dto.driver_position.DriverPositionRequest;
import com.optic.apirest.dto.driver_position.DriverPositionResponse;
import com.optic.apirest.dto.driver_position.NearbyDriverPositionResponse;
import com.optic.apirest.models.DriverPosition;
import com.optic.apirest.models.User;
import com.optic.apirest.repositories.DriverPositionRepository;
import com.optic.apirest.repositories.UserRepository;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DriverPositionService {

    @Autowired
    private DriverPositionRepository driverPositionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate; // esta clase permite crear la consulta de manera manual

    @Transactional
    public DriverPositionResponse create (DriverPositionRequest request) {
        User user = userRepository.findById(request.getIdDriver()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        // el 4326 es un codigo que representa el sistema de coordenadas basandose en la latitud y longitud
        /*GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(request.getLng(), request.getLat()));
        point.setSRID(4326);

        // vamos a averiguar si ya existe informacion en la base de datos, para segun eso
        // mandar solamente a actualizar la informacion o a crearla
        Optional<DriverPosition> existingPosition = driverPositionRepository.findById(request.getIdDriver());

        DriverPosition driverPosition;
        if (existingPosition.isPresent()){
            driverPosition = existingPosition.get();
            driverPosition.setPosition(point);
        }
        else {
            driverPosition = new DriverPosition();
            driverPosition.setUser(user);
            driverPosition.setPosition(point);
        }*/
        String sql = """
            REPLACE INTO drivers_position (id_driver, position)
            VALUES (?,ST_GeomFromText(?, 4326))
        """;
        String point = String.format("POINT(%s %s)", request.getLng(), request.getLat());
        jdbcTemplate.update(sql, request.getIdDriver(), point);

        return new DriverPositionResponse(request.getIdDriver(), request.getLat(), request.getLng());
    }

    @Transactional
    public DriverPositionResponse getDriverPosition(Long idDriver) {
        List<Object[]> result = driverPositionRepository.findDriverPosition(idDriver);
        if (result.isEmpty()){
            throw new RuntimeException("El conductor no existe");
        }

        Object[] row = result.get(0); // el primer resultado (posicion cero)
        Long id = ((Number) row[0]).longValue();
        String positionText = (String) row[1];
        Pattern pattern = Pattern.compile("POINT\\(([-\\d.]+) ([-\\d.]+)\\)");
        Matcher matcher = pattern.matcher(positionText);

        if (matcher.matches()) {
            double lng = Double.parseDouble(matcher.group(1)); // X = lng
            double lat = Double.parseDouble(matcher.group(2)); // Y = lat
            return new DriverPositionResponse(id, lat,lng);
        }
        else {
            throw new RuntimeException("Error al obtener la posicion");
        }
    }

    @Transactional
    public List<NearbyDriverPositionResponse> getNearbyDrivers(double lat, double lng) {
        List<Object[]> results = driverPositionRepository.findNearbyDrivers(lat, lng);
        List<NearbyDriverPositionResponse> response = new ArrayList<>();
        for (Object[] row: results) {
            Long id = ((Number) row[0]).longValue();
            String positionText = (String) row[1];
            Pattern pattern = Pattern.compile("POINT\\(([-\\d.]+) ([-\\d.]+)\\)");
            Matcher matcher = pattern.matcher(positionText);
            double distance = ((Number) row[2]).doubleValue();

            if (matcher.find()) {
                double x = Double.parseDouble(matcher.group(1));
                double y = Double.parseDouble(matcher.group(2));

                NearbyDriverPositionResponse.Position position = new NearbyDriverPositionResponse.Position(x, y);
                response.add(new NearbyDriverPositionResponse(id, position, distance));
            }
        }
        return response;
    }

    @Transactional
    public void delete(Long idDriver) {
        DriverPosition driverPosition = driverPositionRepository.findById(idDriver).orElseThrow(
                () -> new RuntimeException("La posición del conductor no existe")
        );
        driverPositionRepository.delete(driverPosition);
    }

}
