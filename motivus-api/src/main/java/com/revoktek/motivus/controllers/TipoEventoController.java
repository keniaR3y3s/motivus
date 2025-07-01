package com.revoktek.motivus.controllers;

import com.revoktek.motivus.core.constants.request.TipoEventoPath;
import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.services.TipoEventoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(TipoEventoPath.CONTROLLER)
public class TipoEventoController {

    private TipoEventoService tipoEventoService;

    @PostMapping(TipoEventoPath.FIND_ALL_WITH_COUNT)
    public ResponseEntity<?> findAllWithCount(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tipoEventoService.findAllWithCount(filterDTO));
    }

    @PostMapping(TipoEventoPath.FIND_ALL_WITH_AVERAGE_PERCENTAGE)
    public ResponseEntity<?> findAllWithAveragePercentage(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tipoEventoService.findAllWithAveragePercentage(filterDTO));
    }

    @PostMapping(TipoEventoPath.FIND_ALL_WITH_AVERAGE_RESPONSE)
    public ResponseEntity<?> findAllWithAverageResponse(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tipoEventoService.findAllWithAverageResponse(filterDTO));
    }

}
