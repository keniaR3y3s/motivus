package com.revoktek.motivus.controllers;

import com.revoktek.motivus.core.constants.request.EventoBiometricoPath;
import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.FilterDTO;
import com.revoktek.motivus.dto.extras.SaveEventoDTO;
import com.revoktek.motivus.services.EventoBiometricoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(EventoBiometricoPath.CONTROLLER)
public class EventoBiometricoController {

    private EventoBiometricoService eventoBiometricoService;

    @PostMapping(EventoBiometricoPath.FIND_ALL_BY_FILTER)
    public ResponseEntity<?> findAllByFilter(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(eventoBiometricoService.findAllByFilter(filterDTO));
    }

    @PostMapping(EventoBiometricoPath.FIND_ALL_DEVICE_COUNT)
    public ResponseEntity<?> findAllDeviceCount(@RequestBody(required = false) FilterDTO filterDTO) throws BusinessException {
        return ResponseEntity.ok(eventoBiometricoService.findAllDeviceCount(filterDTO));
    }

    @PostMapping(EventoBiometricoPath.SAVE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody SaveEventoDTO saveEventoDTO) throws BusinessException {
        eventoBiometricoService.save(saveEventoDTO);
    }

}
