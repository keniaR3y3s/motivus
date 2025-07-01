package com.revoktek.motivus.services;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.PicoFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.PromedioFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.UsuarioFuncionalidadDTO;

import java.util.List;

public interface TiempoFuncionalidadService {

    List<PromedioFuncionalidadDTO> findAllWithAveragePercentage(FilterDTO filterDTO) throws BusinessException;

    List<ConteoFuncionalidadDTO> findAllWithCount(FilterDTO filterDTO) throws BusinessException;

    List<PicoFuncionalidadDTO> findAllPeakByDate(FilterDTO filterDTO) throws BusinessException;

    List<UsuarioFuncionalidadDTO> findAllWithCountUser(FilterDTO filterDTO) throws BusinessException;
}
