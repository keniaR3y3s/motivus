package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.dto.EventoBiometricoDTO;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoDispositivoDTO;
import com.revoktek.motivus.dto.extras.SaveEventoDTO;
import com.revoktek.motivus.dto.extras.SaveFuncionalidadDTO;
import com.revoktek.motivus.persistence.AppMapper;
import com.revoktek.motivus.persistence.entities.EventoBiometrico;
import com.revoktek.motivus.persistence.entities.ResultadoEvento;
import com.revoktek.motivus.persistence.entities.TiempoFuncionalidad;
import com.revoktek.motivus.persistence.entities.TipoEvento;
import com.revoktek.motivus.persistence.entities.Usuario;
import com.revoktek.motivus.persistence.repositories.EventoBiometricoRepository;
import com.revoktek.motivus.persistence.repositories.ResultadoEventoRepository;
import com.revoktek.motivus.persistence.repositories.TiempoFuncionalidadRepository;
import com.revoktek.motivus.persistence.repositories.TipoEventoRepository;
import com.revoktek.motivus.persistence.repositories.UsuarioRepository;
import com.revoktek.motivus.services.EventoBiometricoService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class EventoBiometricoServiceImpl implements EventoBiometricoService {

    private final TiempoFuncionalidadRepository tiempoFuncionalidadRepository;
    private final EventoBiometricoRepository eventoBiometricoRepository;
    private final ResultadoEventoRepository resultadoEventoRepository;
    private final TipoEventoRepository tipoEventoRepository;
    private final UsuarioRepository usuarioRepository;

    private NominatimService nominatimService;


    private final ApplicationUtil applicationUtil;
    private final MessageProvider messageProvider;
    private final AppMapper appMapper;

    @Override
    public List<EventoBiometricoDTO> findAllByFilter(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllByFilter.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<EventoBiometrico> result = eventoBiometricoRepository.findAllByFilter(
                    filterDTO.getFechaInicio(),
                    filterDTO.getFechaFin(),
                    filterDTO.getResultado(),
                    filterDTO.getOffline(),
                    filterDTO.getValidadoPorTflite(),
                    filterDTO.getTiempoRespuesta(),
                    filterDTO.getOvalAlineado(),
                    filterDTO.getAbierto()
            );
            if (applicationUtil.isEmptyList(result)) {
                log.info("Sin elementos encontrados.");
                return Collections.emptyList();
            }
            log.info("{} elementos encontrados.", result.size());


            return result.stream().map(appMapper::toDto).collect(Collectors.toList());
        } catch (BusinessException e) {
            log.warn(messageProvider.getBusinessWarningConsole(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error(messageProvider.getUnexpectedErrorConsole(this.getClass().getSimpleName(), e.getMessage()), e);
            throw new InternalServerException(e);
        }
    }

    @Override
    public List<ConteoDispositivoDTO> findAllDeviceCount(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithCount.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<ConteoDispositivoDTO> result = eventoBiometricoRepository.findAllDeviceCount(
                    filterDTO.getFechaInicio(),
                    filterDTO.getFechaFin()
            );
            if (applicationUtil.isEmptyList(result)) {
                log.info("Sin elementos encontrados.");
                return Collections.emptyList();
            }
            log.info("{} elementos encontrados.", result.size());


            return result;
        } catch (BusinessException e) {
            log.warn(messageProvider.getBusinessWarningConsole(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error(messageProvider.getUnexpectedErrorConsole(this.getClass().getSimpleName(), e.getMessage()), e);
            throw new InternalServerException(e);
        }
    }

    @Override
    @Transactional
    public void save(SaveEventoDTO saveEventoDTO) throws BusinessException {
        try {

            log.info("Datos front save.saveEventoDTO : {}", applicationUtil.toJson(saveEventoDTO));

            Usuario usuario = usuarioRepository.findByUsuario(saveEventoDTO.getUsuario());
            if (usuario == null) {
                usuario = new Usuario();
                usuario.setUsuario(saveEventoDTO.getUsuario());
                usuarioRepository.save(usuario);
            }

            if (applicationUtil.nonEmptyList(saveEventoDTO.getFuncionalidades())) {
                for (SaveFuncionalidadDTO funcionalidadDTO : saveEventoDTO.getFuncionalidades()) {
                    TiempoFuncionalidad funcionalidad = new TiempoFuncionalidad();
                    funcionalidad.setFuncionalidad(funcionalidadDTO.getFuncionalidad());
                    funcionalidad.setFechaEntrada(funcionalidadDTO.getFechaEntrada());
                    funcionalidad.setFechaSalida(funcionalidadDTO.getFechaSalida());
                    funcionalidad.setUsuario(usuario);
                    tiempoFuncionalidadRepository.save(funcionalidad);

                    if (funcionalidadDTO.getEventoBiometrico() != null) {
                        EventoBiometricoDTO eventoDTO = funcionalidadDTO.getEventoBiometrico();
                        EventoBiometrico evento = new EventoBiometrico();

                        if (eventoDTO.getTipoEvento() != null && eventoDTO.getTipoEvento().getClave() != null) {
                            TipoEvento tipoEvento = tipoEventoRepository.findByClave(eventoDTO.getTipoEvento().getClave());
                            evento.setTipoEvento(tipoEvento);
                        }

                        if (eventoDTO.getResultadoEvento() != null && eventoDTO.getResultadoEvento().getClave() != null) {
                            ResultadoEvento resultadoEvento = resultadoEventoRepository.findByClave(eventoDTO.getResultadoEvento().getClave());
                            evento.setResultadoEvento(resultadoEvento);
                        }

                        evento.setIdTransacional(eventoDTO.getIdTransacional());
                        evento.setFechaHoraDia(eventoDTO.getFechaHoraDia());
                        evento.setPorcentaje(eventoDTO.getPorcentaje());
                        evento.setVersionAndroidDispositivo(saveEventoDTO.getVersionAndroidDispositivo());
                        evento.setDispositivo(saveEventoDTO.getDispositivo());
                        evento.setGps(eventoDTO.getGps());
                        evento.setEntidadFederativa(nominatimService.getFederalEntityName(eventoDTO.getGps()));
                        evento.setResultadoDescripcion(eventoDTO.getResultadoDescripcion());
                        evento.setTiempoRespuestaMs(eventoDTO.getTiempoRespuestaMs());
                        evento.setTiempoProcesoMs(eventoDTO.getTiempoProcesoMs());
                        evento.setOvalAlineado(eventoDTO.getOvalAlineado());
                        evento.setValidadoPorTflite(eventoDTO.getValidadoPorTflite());
                        evento.setOfflineEvento(eventoDTO.getOfflineEvento());
                        evento.setSincronizado(Boolean.TRUE);
                        evento.setUsuario(usuario);
                        eventoBiometricoRepository.save(evento);

                        funcionalidad.setEventoBiometrico(evento);
                        tiempoFuncionalidadRepository.save(funcionalidad);

                    }

                }
            }


        } catch (BusinessException e) {
            log.warn(messageProvider.getBusinessWarningConsole(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error(messageProvider.getUnexpectedErrorConsole(this.getClass().getSimpleName(), e.getMessage()), e);
            throw new InternalServerException(e);
        }
    }
}