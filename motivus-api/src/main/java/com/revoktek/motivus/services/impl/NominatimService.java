package com.revoktek.motivus.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class NominatimService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getFederalEntity(String gps) {
        if (gps == null || gps.trim().isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> resultSet = new LinkedHashSet<>();
        String[] partes = gps.split("\\|");

        for (String parte : partes) {
            if (parte == null || parte.trim().isEmpty()) continue;

            String[] latLon = parte.trim().split(",");
            if (latLon.length != 2) continue;

            try {
                double lat = Double.parseDouble(latLon[0].trim());
                double lon = Double.parseDouble(latLon[1].trim());
                String federalEntity = getRequest(lat, lon);
                if (federalEntity != null && !federalEntity.trim().isEmpty()) {
                    resultSet.add(federalEntity.trim());
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Coordenada inválida: '" + parte + "' - " + nfe.getMessage());
            } catch (Exception e) {
                System.err.println("Error al obtener entidad federativa para: '" + parte + "' - " + e.getMessage());
            }
        }

        return new ArrayList<>(resultSet);
    }


    private String getRequest(double lat, double lon) {
        try {
            String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + lat + "&lon=" + lon + "&zoom=5&addressdetails=1";

            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(json);

            if (root.has("address") && root.get("address").has("state")) {
                return root.get("address").get("state").asText();
            } else if (root.has("address") && root.get("address").has("region")) {
                return root.get("address").get("region").asText();
            }
        } catch (Exception e) {
            System.out.println("Error en consulta: " + e.getMessage());
        }
        return null;

    }
}
