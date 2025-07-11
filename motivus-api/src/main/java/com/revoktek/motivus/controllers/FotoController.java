package com.revoktek.motivus.controllers;

import com.revoktek.motivus.core.constants.request.FotoPath;
import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.dto.extras.SaveFotoDTO;
import com.revoktek.motivus.services.FotoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(FotoPath.CONTROLLER)
public class FotoController {

    private FotoService fotoService;

    @PostMapping(FotoPath.SAVE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody SaveFotoDTO saveFotoDTO) throws BusinessException {
        fotoService.save(saveFotoDTO);
    }

}
