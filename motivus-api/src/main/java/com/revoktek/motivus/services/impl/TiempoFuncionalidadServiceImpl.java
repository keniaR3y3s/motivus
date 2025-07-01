package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.core.utils.MapperUtil;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.PicoFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.PromedioFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.UsuarioFuncionalidadDTO;
import com.revoktek.motivus.persistence.AppMapper;
import com.revoktek.motivus.persistence.repositories.TiempoFuncionalidadRepository;
import com.revoktek.motivus.services.TiempoFuncionalidadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class TiempoFuncionalidadServiceImpl implements TiempoFuncionalidadService {

    private final TiempoFuncionalidadRepository tiempoFuncionalidadRepository;
    private final ApplicationUtil applicationUtil;
    private final MessageProvider messageProvider;
    private final AppMapper appMapper;
    private final MapperUtil mapperUtil;


    @Override
    public List<PromedioFuncionalidadDTO> findAllWithAveragePercentage(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithAveragePercentage.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<PromedioFuncionalidadDTO> result = tiempoFuncionalidadRepository.findAllWithAveragePercentage(filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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
    public List<ConteoFuncionalidadDTO> findAllWithCount(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithCount.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<ConteoFuncionalidadDTO> result = tiempoFuncionalidadRepository.findAllWithCount(filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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
    public List<PicoFuncionalidadDTO> findAllPeakByDate(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithCount.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<PicoFuncionalidadDTO> result = tiempoFuncionalidadRepository.findAllPeakByDate(filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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
    public List<UsuarioFuncionalidadDTO> findAllWithCountUser(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithCountUser.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<UsuarioFuncionalidadDTO> result = tiempoFuncionalidadRepository.findAllWithCountUser(filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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