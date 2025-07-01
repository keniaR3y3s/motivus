package com.revoktek.motivus.dto.extras;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.revoktek.motivus.dto.UsuarioDTO;
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
public class UsuarioFuncionalidadDTO {
    private UsuarioDTO usuario;
    private String funcionalidad;
    private Long cantidad;

    public UsuarioFuncionalidadDTO(Long idUsuario, String usuario, String funcionalidad, Long cantidad) {
        this.usuario = new UsuarioDTO(idUsuario, usuario, null, null);
        this.funcionalidad = funcionalidad;
        this.cantidad = cantidad;
    }
}
