package com.revoktek.motivus.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revoktek.motivus.dto.EntidadFederativaDTO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.List;

@Service
public class GpsService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<EntidadFederativaDTO> entidadesFederativas;

    public GpsService() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("gps/entidades-federativas-mexico.json").getInputStream();
            entidadesFederativas = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar estados de México desde JSON", e);
        }
    }
    public String getFederalEntityName(String gps) {
        if (gps == null || !gps.contains(",")) {
            return null;
        }

        try {
            String[] partes = gps.trim().split(",");
            double lat = Double.parseDouble(partes[0].trim());
            double lon = Double.parseDouble(partes[1].trim());

            return entidadesFederativas.stream()
                    .filter(estado ->
                            lat >= estado.getLatMin() && lat <= estado.getLatMax() &&
                            lon >= estado.getLonMin() && lon <= estado.getLonMax())
                    .map(EntidadFederativaDTO::getNombre)
                    .findFirst()
                    .orElse("Desconocido");

        } catch (Exception e) {
            return "Desconocido";
        }
    }
}
