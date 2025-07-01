package com.revoktek.motivus.dto.extras;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class PicoFuncionalidadDTO {
    private String funcionalidad;
    private LocalDate fecha;
    private LocalTime duracion;

    public PicoFuncionalidadDTO(String funcionalidad, Object fecha, Number segundos) {
        this.funcionalidad = funcionalidad;
        if (fecha instanceof java.sql.Date) {
            this.fecha = ((java.sql.Date) fecha).toLocalDate();
        } else if (fecha instanceof java.util.Date) {
            this.fecha = ((java.util.Date) fecha).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            this.fecha = null;
        }
        this.duracion = LocalTime.ofSecondOfDay(segundos != null ? segundos.longValue() : 0L);
    }
}