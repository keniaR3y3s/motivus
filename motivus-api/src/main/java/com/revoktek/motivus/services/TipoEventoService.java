package com.revoktek.motivus.services;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoTipoEventoDTO;
import com.revoktek.motivus.dto.extras.PromedioRespuestaDTO;
import com.revoktek.motivus.dto.extras.PromedioValidacionDTO;

import java.util.List;

public interface TipoEventoService {

    List<ConteoTipoEventoDTO> findAllWithCount(FilterDTO filterDTO) throws BusinessException;

    List<PromedioValidacionDTO> findAllWithAveragePercentage(FilterDTO filterDTO) throws BusinessException;

    List<PromedioRespuestaDTO> findAllWithAverageResponse(FilterDTO filterDTO) throws BusinessException;
}
