package com.revoktek.motivus.persistence;

import com.revoktek.motivus.dto.EventoBiometricoDTO;
import com.revoktek.motivus.dto.ResultadoEventoDTO;
import com.revoktek.motivus.dto.TipoEventoDTO;
import com.revoktek.motivus.dto.UsuarioDTO;
import com.revoktek.motivus.persistence.entities.EventoBiometrico;
import com.revoktek.motivus.persistence.entities.ResultadoEvento;
import com.revoktek.motivus.persistence.entities.TipoEvento;
import com.revoktek.motivus.persistence.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AppMapper {

    public EventoBiometricoDTO toDto(EventoBiometrico entity) {
        if (entity == null) return null;

        EventoBiometricoDTO dto = new EventoBiometricoDTO();
        dto.setIdEventoBiometrico(entity.getIdEventoBiometrico());
        dto.setIdTransacional(entity.getIdTransacional());
        dto.setFechaHoraDia(entity.getFechaHoraDia());
        dto.setPorcentaje(entity.getPorcentaje());
        dto.setVersionAndroidDispositivo(entity.getVersionAndroidDispositivo());
        dto.setGps(entity.getGps());
        dto.setEntidadFederativa(entity.getEntidadFederativa());
        dto.setResultadoDescripcion(entity.getResultadoDescripcion());
        dto.setTiempoRespuestaMs(entity.getTiempoRespuestaMs());
        dto.setTiempoProcesoMs(entity.getTiempoProcesoMs());
        dto.setOvalAlineado(entity.getOvalAlineado());
        dto.setOfflineEvento(entity.getOfflineEvento());
        dto.setSincronizado(entity.getSincronizado());
        dto.setDispositivo(entity.getDispositivo());
        dto.setOfflineEvento(entity.getOfflineEvento());
        dto.setValidadoPorTflite(entity.getValidadoPorTflite());

        dto.setUsuario(toDto(entity.getUsuario()));
        dto.setTipoEvento(toDto(entity.getTipoEvento()));
        dto.setResultadoEvento(toDto(entity.getResultadoEvento()));

        return dto;
    }

    public EventoBiometrico toEntity(EventoBiometricoDTO dto) {
        if (dto == null) return null;

        EventoBiometrico entity = new EventoBiometrico();
        entity.setIdEventoBiometrico(dto.getIdEventoBiometrico());
        entity.setIdTransacional(dto.getIdTransacional());
        entity.setFechaHoraDia(dto.getFechaHoraDia());
        entity.setSincronizado(dto.getSincronizado());

        entity.setUsuario(toEntity(dto.getUsuario()));
        entity.setTipoEvento(toEntity(dto.getTipoEvento()));
        entity.setResultadoEvento(toEntity(dto.getResultadoEvento()));

        return entity;
    }

    public UsuarioDTO toDto(Usuario entity) {
        if (entity == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(entity.getIdUsuario());
        dto.setUsuario(entity.getUsuario());
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario entity = new Usuario();
        entity.setIdUsuario(dto.getIdUsuario());
        entity.setUsuario(dto.getUsuario());
        return entity;
    }

    public TipoEventoDTO toDto(TipoEvento entity) {
        if (entity == null) return null;
        TipoEventoDTO dto = new TipoEventoDTO();
        dto.setIdTipoEvento(entity.getIdTipoEvento());
        dto.setClave(entity.getClave());
        dto.setDetalle(entity.getDetalle());
        dto.setMetodo(entity.getMetodo());
        return dto;
    }

    public TipoEvento toEntity(TipoEventoDTO dto) {
        if (dto == null) return null;
        TipoEvento entity = new TipoEvento();
        entity.setIdTipoEvento(dto.getIdTipoEvento());
        entity.setClave(dto.getClave());
        entity.setDetalle(dto.getDetalle());
        entity.setMetodo(dto.getMetodo());
        return entity;
    }

    public ResultadoEventoDTO toDto(ResultadoEvento entity) {
        if (entity == null) return null;
        ResultadoEventoDTO dto = new ResultadoEventoDTO();
        dto.setIdResultadoEvento(entity.getIdResultadoEvento());
        dto.setClave(entity.getClave());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    public ResultadoEvento toEntity(ResultadoEventoDTO dto) {
        if (dto == null) return null;
        ResultadoEvento entity = new ResultadoEvento();
        entity.setIdResultadoEvento(dto.getIdResultadoEvento());
        entity.setClave(dto.getClave());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }

}

