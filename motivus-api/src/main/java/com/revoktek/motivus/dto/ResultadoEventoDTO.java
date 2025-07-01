package com.revoktek.motivus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultadoEventoDTO {

    private Long idResultadoEvento;
    private String clave;
    private String descripcion;
    private List<EventoBiometricoDTO> eventos;

}
