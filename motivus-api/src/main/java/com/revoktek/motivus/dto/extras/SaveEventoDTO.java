package com.revoktek.motivus.dto.extras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaveEventoDTO {

    private String usuario;
    private String dispositivo;
    private String versionAndroidDispositivo;
    private List<SaveFuncionalidadDTO> funcionalidades;
}
