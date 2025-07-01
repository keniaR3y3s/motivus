package com.revoktek.motivus.dto.extras;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.revoktek.motivus.dto.EventoBiometricoDTO;
import com.revoktek.motivus.dto.TipoEventoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConteoTipoEventoDTO extends TipoEventoDTO {
    private TipoEventoDTO tipoEvento;
    private Long total;

    public ConteoTipoEventoDTO(Long idTipoEvento, String clave, String detalle, String metodo, List<EventoBiometricoDTO> eventos, Long total) {
        this.tipoEvento = new TipoEventoDTO(idTipoEvento, clave, detalle, metodo, eventos);
        this.total = total;
    }
}
