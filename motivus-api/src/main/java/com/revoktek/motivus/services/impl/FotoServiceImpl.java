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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;


@Slf4j
@Service
public class FotoServiceImpl implements FotoService {

    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MessageProvider messageProvider;

    @Value("${app.imagenes.directorio-base}")
    private String directorioBase;

    @Override
    @Transactional
    public void save(SaveFotoDTO dto) throws BusinessException {
        try {

            Usuario usuario = usuarioRepository.findByUsuario(dto.getUsuario());
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setUsuario(dto.getUsuario());
                usuarioRepository.save(usuario);
            }

            String TIPO_HISTORIAL = "Historial";
            String tipo = dto.getTipo() == null ? TIPO_HISTORIAL : dto.getTipo();

            byte[] imagenBytes;
            try {
                imagenBytes = Base64.getDecoder().decode(dto.getImagenBase64());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("La imagen no está correctamente codificada.");
            }

            Path carpetaUsuario = Paths.get(directorioBase, usuario.getUsuario());
            if (!Files.exists(carpetaUsuario)) {
                Files.createDirectories(carpetaUsuario);
            }

            String extension = "jpg";

            String fechaStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = usuario.getUsuario() + "_" + tipo.toLowerCase() + "_" + fechaStr + "." + extension;

            Path archivoImagen = carpetaUsuario.resolve(nombreArchivo);

            Files.write(archivoImagen, imagenBytes);

            String rutaRelativa = usuario.getUsuario() + "/" + nombreArchivo;

            Foto foto = new Foto();
            foto.setImagen(rutaRelativa);
            foto.setTipo(tipo);
            foto.setFechaCaptura(LocalDateTime.now());
            foto.setActivo(true);
            foto.setUsuario(usuario);

            List<Foto> principales = fotoRepository.findAllByTipoAndActivoAndUsuario(foto.getTipo(), foto.getActivo(), usuario);
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
        } catch (IOException e) {
            log.error("Error guardando la imagen en disco: " + e.getMessage(), e);
            throw new InternalServerException("Error guardando la imagen.");
        } catch (Exception e) {
            log.error(messageProvider.getUnexpectedErrorConsole(this.getClass().getSimpleName(), e.getMessage()), e);
            throw new InternalServerException(e);
        }
    }

}