package com.revoktek.motivus.dto.extras;

import com.revoktek.motivus.dto.EventoBiometricoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaveFuncionalidadDTO {

    private String funcionalidad;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private EventoBiometricoDTO eventoBiometrico;

}
