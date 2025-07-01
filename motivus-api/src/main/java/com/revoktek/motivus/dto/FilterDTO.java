package com.revoktek.motivus.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilterDTO {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private String resultado;
    private Boolean offline = null;
    private Boolean validadoPorTflite = null;
    private String ovalAlineado = null;
    private Integer tiempoRespuesta;
    private Boolean abierto = null;
    private List<String> funcionalidades = null;


}
