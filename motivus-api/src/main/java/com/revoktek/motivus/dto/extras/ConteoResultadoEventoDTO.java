package com.revoktek.motivus.dto.extras;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.revoktek.motivus.dto.ResultadoEventoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConteoResultadoEventoDTO {

    private ResultadoEventoDTO resultado;
    private Long total;

    public ConteoResultadoEventoDTO(Long idResultadoEvento, String clave, String descripcion,  Long total) {
        ResultadoEventoDTO resultado = new ResultadoEventoDTO();
        resultado.setIdResultadoEvento(idResultadoEvento);
        resultado.setClave(clave);
        resultado.setDescripcion(descripcion);
        this.resultado = resultado;
        this.total = total;
    }
}
