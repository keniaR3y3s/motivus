package com.revoktek.motivus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoBiometricoDTO {


    private Long idEventoBiometrico;
    private String idTransacional;
    private LocalDateTime fechaHoraDia;
    private BigDecimal porcentaje;
    private String versionAndroidDispositivo;
    private String dispositivo;
    private String gps;
    private String entidadFederativa;
    private String resultadoDescripcion;
    private Integer tiempoRespuestaMs;
    private Integer tiempoProcesoMs;
    private String ovalAlineado;
    private Boolean validadoPorTflite;
    private Boolean offlineEvento;
    private Boolean sincronizado = false;
    private UsuarioDTO usuario;
    private TipoEventoDTO tipoEvento;
    private ResultadoEventoDTO resultadoEvento;
    private List<TiempoFuncionalidadDTO> tiempos;

}
