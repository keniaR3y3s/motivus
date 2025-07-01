package com.revoktek.motivus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TiempoFuncionalidadDTO {

    private Long idTiempoFuncionalidad;
    private String funcionalidad;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private Integer duracionHoras;
    private Integer duracionMinutos;
    private Integer duracionSoloSegundos;
    private UsuarioDTO usuario;
    private EventoBiometricoDTO eventoBiometrico;

}
