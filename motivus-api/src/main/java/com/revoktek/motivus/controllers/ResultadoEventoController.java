package com.revoktek.motivus.controllers;

import com.revoktek.motivus.core.constants.request.ResultadoEventoPath;
import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.services.ResultadoEventoService;
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
@RequestMapping(ResultadoEventoPath.CONTROLLER)
public class ResultadoEventoController {

    private ResultadoEventoService resultadoEventoService;

    @PostMapping(ResultadoEventoPath.FIND_ALL_WITH_COUNT)
    public ResponseEntity<?> findAllWithCount(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(resultadoEventoService.findAllWithCount(filterDTO));
    }

    @PostMapping(ResultadoEventoPath.FIND_ALL_WITH_PERCENTAGE)
    public ResponseEntity<?> findAllWithPercentage(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(resultadoEventoService.findAllWithPercentage(filterDTO));
    }

}
