package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.core.utils.MapperUtil;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.TipoEventoDTO;
import com.revoktek.motivus.dto.extras.ConteoTipoEventoDTO;
import com.revoktek.motivus.dto.extras.PromedioRespuestaDTO;
import com.revoktek.motivus.dto.extras.PromedioValidacionDTO;
import com.revoktek.motivus.persistence.AppMapper;
import com.revoktek.motivus.persistence.repositories.TipoEventoRepository;
import com.revoktek.motivus.services.TipoEventoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class TipoEventoServiceImpl implements TipoEventoService {

    private final TipoEventoRepository tipoEventoRepository;
    private final ApplicationUtil applicationUtil;
    private final MessageProvider messageProvider;
    private final AppMapper appMapper;
    private final MapperUtil mapperUtil;


    @Override
    public List<ConteoTipoEventoDTO> findAllWithCount(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithCount.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<ConteoTipoEventoDTO> result = tipoEventoRepository.findAllWithCount(filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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
    public List<PromedioValidacionDTO> findAllWithAveragePercentage(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithAveragePercentage.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<PromedioValidacionDTO> result = tipoEventoRepository.findAllWithAveragePercentage(filterDTO.getFechaInicio(),filterDTO.getFechaFin());
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
    public List<PromedioRespuestaDTO> findAllWithAverageResponse(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithAverageResponse.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<PromedioRespuestaDTO> result = tipoEventoRepository.findAllWithAverageResponse(filterDTO.getFechaInicio(),filterDTO.getFechaFin());
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