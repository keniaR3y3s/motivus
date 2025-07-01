package com.revoktek.motivus.dto.extras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PromedioRespuestaDTO {

    private String tipoEvento;
    private Double tiempoRespuestaPromedioMs;

    public PromedioRespuestaDTO(String tipoEvento, Double tiempoRespuestaPromedioMs) {
        this.tipoEvento = tipoEvento;
        this.tiempoRespuestaPromedioMs = tiempoRespuestaPromedioMs == null ? 0.0 : tiempoRespuestaPromedioMs;
    }
}