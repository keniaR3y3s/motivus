package com.revoktek.motivus.dto.extras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromedioFuncionalidadDTO {

    private String funcionalidad;
    private LocalTime promedio;

    public PromedioFuncionalidadDTO(String funcionalidad, Double segundos) {
        this.funcionalidad = funcionalidad;
        this.promedio = LocalTime.ofSecondOfDay(segundos != null ? segundos.intValue() : 0);
    }
}
