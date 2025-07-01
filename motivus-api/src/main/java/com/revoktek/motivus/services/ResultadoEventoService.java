package com.revoktek.motivus.services;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoResultadoEventoDTO;
import com.revoktek.motivus.dto.extras.PorcentajeResultadoDTO;

import java.util.List;

public interface ResultadoEventoService {

    List<ConteoResultadoEventoDTO> findAllWithCount(FilterDTO filterDTO) throws BusinessException;

    List<PorcentajeResultadoDTO> findAllWithPercentage(FilterDTO filterDTO) throws BusinessException;
}
