package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.dto.extras.ConteoDispositivoDTO;
import com.revoktek.motivus.persistence.entities.EventoBiometrico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EventoBiometricoRepository extends JpaRepository<EventoBiometrico, Long> {

    @Query("""
                SELECT DISTINCT e
                FROM EventoBiometrico e
                INNER JOIN FETCH e.usuario u
                INNER JOIN FETCH e.tipoEvento te
                LEFT JOIN FETCH e.resultadoEvento r
                LEFT JOIN FETCH e.tiempos tiempo
                WHERE
                    ( (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (e.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin) )
                    AND ( (:resultado IS NULL) OR (LOWER(r.clave) = LOWER(:resultado) ) )
                    AND ( (:offline IS NULL) OR ( e.offlineEvento = :offline) )
                    AND ( (:validadoPorTflite IS NULL) OR ( e.offlineEvento = :validadoPorTflite) )
                    AND ( (:tiempoRespuesta IS NULL) OR ( e.tiempoRespuestaMs > :tiempoRespuesta) )
                    AND ( (:ovalAlineado IS NULL) OR ( LOWER(e.ovalAlineado) = LOWER(:ovalAlineado) ) )
                    AND ( (:abiertos IS NULL )
                         OR (:abiertos = true AND  tiempo.fechaSalida IS NULL )
                         OR (:abiertos = false AND  tiempo.fechaSalida IS NOT NULL )
                    )
            """)
    List<EventoBiometrico> findAllByFilter(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("resultado") String resultado,
            @Param("offline") Boolean offline,
            @Param("validadoPorTflite") Boolean validadoPorTflite,
            @Param("tiempoRespuesta") Integer tiempoRespuesta,
            @Param("ovalAlineado") String ovalAlineado,
            @Param("abiertos") Boolean abierto
    );

    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.ConteoDispositivoDTO(
                    e.dispositivo,
                    COUNT(e)
                )
                FROM EventoBiometrico e
                WHERE e.dispositivo IS NOT NULL
                 AND  ( (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (e.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin) )
                GROUP BY e.dispositivo
                ORDER BY COUNT(e) DESC
            """)
    List<ConteoDispositivoDTO> findAllDeviceCount(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}