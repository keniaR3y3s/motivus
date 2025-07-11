package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.dto.extras.SaveFotoDTO;
import com.revoktek.motivus.persistence.entities.Foto;
import com.revoktek.motivus.persistence.entities.Usuario;
import com.revoktek.motivus.persistence.repositories.FotoRepository;
import com.revoktek.motivus.persistence.repositories.UsuarioRepository;
import com.revoktek.motivus.services.FotoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class FotoServiceImpl implements FotoService {

    private final FotoRepository fotoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ApplicationUtil applicationUtil;
    private final MessageProvider messageProvider;


    @Override
    @Transactional
    public void save(SaveFotoDTO dto) throws BusinessException {
        try {

            log.info("Datos front save.saveFotoDTO : {}", applicationUtil.toJson(dto));

            Usuario usuario = usuarioRepository.findByUsuario(dto.getUsuario());
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setUsuario(dto.getUsuario());
                usuarioRepository.save(usuario);
            }

            String TIPO_HISTORIAL = "Historial";
            String FUNCIONALIDAD_REGISTRO = "Registro";

            byte[] imagenBytes;
            try {
                imagenBytes = Base64.getDecoder().decode(dto.getImagenBase64());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("La imagen no está correctamente codificada.");
            }

            Foto foto = new Foto();
            foto.setImagen(imagenBytes);
            foto.setTipo(dto.getTipo() == null ? TIPO_HISTORIAL : dto.getTipo());
            foto.setFuncionalidad(dto.getFuncionalidad() == null ? FUNCIONALIDAD_REGISTRO : dto.getFuncionalidad());
            foto.setFechaCaptura(LocalDateTime.now());
            foto.setActivo(true);
            foto.setUsuario(usuario);

            List<Foto> principales = fotoRepository.findAllByTipoAndActivoAndFuncionalidadAndUsuario(foto.getTipo(), foto.getActivo(), foto.getFuncionalidad(), usuario);
            if (principales != null && !principales.isEmpty()) {
                for (Foto principal : principales) {
                    principal.setActivo(false);
                    fotoRepository.save(principal);
                }
            }

            fotoRepository.save(foto);


        } catch (BusinessException e) {
            log.warn(messageProvider.getBusinessWarningConsole(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error(messageProvider.getUnexpectedErrorConsole(this.getClass().getSimpleName(), e.getMessage()), e);
            throw new InternalServerException(e);
        }
    }
}