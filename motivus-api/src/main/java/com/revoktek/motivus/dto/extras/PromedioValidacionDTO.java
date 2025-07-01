package com.revoktek.motivus.dto.extras;

import com.revoktek.motivus.dto.TipoEventoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromedioValidacionDTO {

    private TipoEventoDTO tipoEvento;
    private Double promedioPorcentaje;

    public PromedioValidacionDTO(Long idTipoEvento, String clave, String detalle, String metodo, Double promedioPorcentaje) {
        tipoEvento = new TipoEventoDTO();
        tipoEvento.setIdTipoEvento(idTipoEvento);
        tipoEvento.setClave(clave);
        tipoEvento.setDetalle(detalle);
        tipoEvento.setMetodo(metodo);
        setPromedioPorcentaje(promedioPorcentaje == null ? 0.0 : promedioPorcentaje);
    }
}
