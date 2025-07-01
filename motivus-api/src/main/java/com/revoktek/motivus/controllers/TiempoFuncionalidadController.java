package com.revoktek.motivus.controllers;

import com.revoktek.motivus.core.constants.request.TiempoFuncionalidadPath;
import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.services.TiempoFuncionalidadService;
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
@RequestMapping(TiempoFuncionalidadPath.CONTROLLER)
public class TiempoFuncionalidadController {

    private TiempoFuncionalidadService tiempoFuncionalidadService;

    @PostMapping(TiempoFuncionalidadPath.FIND_ALL_WITH_AVERAGE)
    public ResponseEntity<?> findAllWithAveragePercentage(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tiempoFuncionalidadService.findAllWithAveragePercentage(filterDTO));
    }

    @PostMapping(TiempoFuncionalidadPath.FIND_ALL_WITH_COUNT)
    public ResponseEntity<?> findAllWithCount(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tiempoFuncionalidadService.findAllWithCount(filterDTO));
    }

    @PostMapping(TiempoFuncionalidadPath.FIND_ALL_PEAK_DATE)
    public ResponseEntity<?> findAllPeakByDate(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tiempoFuncionalidadService.findAllPeakByDate(filterDTO));
    }

    @PostMapping(TiempoFuncionalidadPath.FIND_ALL_WITH_COUNT_USER)
    public ResponseEntity<?> findAllWithCountUser(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(tiempoFuncionalidadService.findAllWithCountUser(filterDTO));
    }

}
