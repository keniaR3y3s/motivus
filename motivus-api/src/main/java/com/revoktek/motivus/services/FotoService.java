package com.revoktek.motivus.services;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.extras.SaveFotoDTO;

public interface FotoService {

    void save(SaveFotoDTO saveFotoDTO) throws BusinessException;
}
