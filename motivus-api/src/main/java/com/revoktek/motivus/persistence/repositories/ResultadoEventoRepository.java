package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.dto.extras.ConteoResultadoEventoDTO;
import com.revoktek.motivus.dto.extras.PorcentajeResultadoDTO;
import com.revoktek.motivus.persistence.entities.ResultadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ResultadoEventoRepository extends JpaRepository<ResultadoEvento, Long> {

    @Query("""
        SELECT new com.revoktek.motivus.dto.extras.ConteoResultadoEventoDTO(
           resultado.idResultadoEvento,
           resultado.clave,
           resultado.descripcion,
            COUNT(evento)
        )
        FROM ResultadoEvento resultado
        LEFT JOIN resultado.eventos evento
        WHERE
            ( (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (evento.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin) )
        GROUP BY resultado.idResultadoEvento, resultado.clave, resultado.descripcion
    """)
    List<ConteoResultadoEventoDTO> findAllWithCount(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("""
    SELECT new com.revoktek.motivus.dto.extras.PorcentajeResultadoDTO(
        r.clave,
        CASE 
            WHEN total.totalCount = 0 THEN 0
            ELSE (COUNT(e) * 100.0 / total.totalCount)
        END
    )
    FROM ResultadoEvento r
    LEFT JOIN r.eventos e
        ON (:offline IS NULL OR e.offlineEvento = :offline)
       AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR e.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin)
    , (SELECT COUNT(ev) AS totalCount FROM EventoBiometrico ev
       WHERE (:offline IS NULL OR ev.offlineEvento = :offline)
         AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR ev.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin)
      ) total
    GROUP BY r.clave
    ORDER BY porcentaje DESC
""")
    List<PorcentajeResultadoDTO> findAllWithPercentage(
            @Param("offline") Boolean offline,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );


}