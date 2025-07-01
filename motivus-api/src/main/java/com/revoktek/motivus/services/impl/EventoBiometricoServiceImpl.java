package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.dto.EventoBiometricoDTO;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoDispositivoDTO;
import com.revoktek.motivus.persistence.AppMapper;
import com.revoktek.motivus.persistence.entities.EventoBiometrico;
import com.revoktek.motivus.persistence.repositories.EventoBiometricoRepository;
import com.revoktek.motivus.services.EventoBiometricoService;
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

    private final EventoBiometricoRepository eventoBiometricoRepository;
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
}