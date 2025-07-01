package com.revoktek.motivus.services;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.EventoBiometricoDTO;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.ConteoDispositivoDTO;

import java.util.List;

public interface EventoBiometricoService {

    List<EventoBiometricoDTO> findAllByFilter(FilterDTO filterDTO) throws BusinessException;

    List<ConteoDispositivoDTO> findAllDeviceCount(FilterDTO filterDTO)  throws BusinessException;
}
