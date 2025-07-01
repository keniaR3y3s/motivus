package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.core.utils.MapperUtil;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoResultadoEventoDTO;
import com.revoktek.motivus.dto.extras.PorcentajeResultadoDTO;
import com.revoktek.motivus.persistence.AppMapper;
import com.revoktek.motivus.persistence.repositories.ResultadoEventoRepository;
import com.revoktek.motivus.services.ResultadoEventoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class ResultadoEventoServiceImpl implements ResultadoEventoService {

    private final ResultadoEventoRepository resultadoEventoRepository;
    private final ApplicationUtil applicationUtil;
    private final MessageProvider messageProvider;
    private final AppMapper appMapper;
    private final MapperUtil mapperUtil;


    @Override
    public List<ConteoResultadoEventoDTO> findAllWithCount(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithCount.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<ConteoResultadoEventoDTO> result = resultadoEventoRepository.findAllWithCount(filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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
    public List<PorcentajeResultadoDTO> findAllWithPercentage(FilterDTO filterDTO) throws BusinessException {
        try {

            filterDTO = applicationUtil.isNull(filterDTO) ? new FilterDTO() : filterDTO;

            log.info("Datos front findAllWithPercentage.filterDTO : {}", applicationUtil.toJson(filterDTO));

            List<PorcentajeResultadoDTO> result = resultadoEventoRepository.findAllWithPercentage(filterDTO.getOffline(), filterDTO.getFechaInicio(), filterDTO.getFechaFin());
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