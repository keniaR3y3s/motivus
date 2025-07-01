package com.revoktek.motivus.dto.extras;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConteoFuncionalidadDTO {

    private String funcionalidad;
    private LocalTime duracion;

    public ConteoFuncionalidadDTO(String funcionalidad, Number  segundos) {
        this.funcionalidad = funcionalidad;
        long seg = segundos != null ? segundos.longValue() : 0L;
        this.duracion = LocalTime.ofSecondOfDay(seg);
    }

}
