package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.dto.extras.ConteoTipoEventoDTO;
import com.revoktek.motivus.dto.extras.PromedioRespuestaDTO;
import com.revoktek.motivus.dto.extras.PromedioValidacionDTO;
import com.revoktek.motivus.persistence.entities.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long> {

    @Query("""
             SELECT new com.revoktek.motivus.dto.extras.ConteoTipoEventoDTO(
               tipoEvento.idTipoEvento,
               tipoEvento.clave,
               tipoEvento.detalle,
               tipoEvento.metodo,
               NULL,
               COUNT(evento)
             ) FROM TipoEvento tipoEvento
            LEFT JOIN tipoEvento.eventos evento ON (:fechaInicio IS NULL OR :fechaFin IS NULL OR evento.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin)
            GROUP BY tipoEvento.idTipoEvento, tipoEvento.clave, tipoEvento.detalle, tipoEvento.metodo
            """)
    List<ConteoTipoEventoDTO> findAllWithCount(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.PromedioValidacionDTO(
                    te.idTipoEvento,
                    te.clave,
                    te.detalle,
                    te.metodo,
                    AVG(e.porcentaje)
                )
                FROM TipoEvento te
                LEFT JOIN te.eventos e ON (:fechaInicio IS NULL OR :fechaFin IS NULL OR e.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin)
                GROUP BY te.idTipoEvento, te.clave, te.detalle, te.metodo
            """)
    List<PromedioValidacionDTO> findAllWithAveragePercentage(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );


    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.PromedioRespuestaDTO(
                    te.clave,
                    AVG(e.tiempoRespuestaMs)
                )
                FROM TipoEvento te
                LEFT JOIN te.eventos e ON (:fechaInicio IS NULL OR :fechaFin IS NULL OR e.fechaHoraDia BETWEEN :fechaInicio AND :fechaFin)
                GROUP BY  te.clave
                ORDER BY AVG(e.tiempoRespuestaMs) DESC
            """)
    List<PromedioRespuestaDTO> findAllWithAverageResponse(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                          @Param("fechaFin") LocalDateTime fechaFin);

    TipoEvento findByClave(String clave);
}