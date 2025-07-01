package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.dto.extras.ConteoFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.PicoFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.PromedioFuncionalidadDTO;
import com.revoktek.motivus.dto.extras.UsuarioFuncionalidadDTO;
import com.revoktek.motivus.persistence.entities.TiempoFuncionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TiempoFuncionalidadRepository extends JpaRepository<TiempoFuncionalidad, Long> {

    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.PromedioFuncionalidadDTO(
                    t.funcionalidad,
                     AVG(TIMESTAMPDIFF(SECOND, t.fechaEntrada, t.fechaSalida))
                )
                FROM TiempoFuncionalidad t
                WHERE t.fechaSalida IS NOT NULL
                GROUP BY t.funcionalidad
                ORDER BY t.funcionalidad
            """)
    List<PromedioFuncionalidadDTO> findAllWithAveragePercentage(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.ConteoFuncionalidadDTO(
                    t.funcionalidad,
                    SUM(FUNCTION('TIMESTAMPDIFF', SECOND, t.fechaEntrada, t.fechaSalida))
                )
                FROM TiempoFuncionalidad t
                WHERE t.fechaSalida IS NOT NULL
                  AND ( (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (t.fechaEntrada BETWEEN :fechaInicio AND :fechaFin) )
                GROUP BY t.funcionalidad
                ORDER BY SUM(FUNCTION('TIMESTAMPDIFF', SECOND, t.fechaEntrada, t.fechaSalida)) DESC
            """)
    List<ConteoFuncionalidadDTO> findAllWithCount(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.PicoFuncionalidadDTO(
                    t.funcionalidad,
                    FUNCTION('DATE', t.fechaEntrada),
                    SUM(FUNCTION('TIMESTAMPDIFF', SECOND, t.fechaEntrada, t.fechaSalida))
                )
                FROM TiempoFuncionalidad t
                WHERE t.fechaSalida IS NOT NULL
                  AND ( (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (t.fechaEntrada BETWEEN :fechaInicio AND :fechaFin) )
                GROUP BY t.funcionalidad, FUNCTION('DATE', t.fechaEntrada)
                ORDER BY SUM(FUNCTION('TIMESTAMPDIFF', SECOND, t.fechaEntrada, t.fechaSalida)) DESC
            """)
    List<PicoFuncionalidadDTO> findAllPeakByDate(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );


    @Query("""
                SELECT new com.revoktek.motivus.dto.extras.UsuarioFuncionalidadDTO(
                    usuario.idUsuario,
                    usuario.usuario,
                    t.funcionalidad,
                    COUNT(t.idTiempoFuncionalidad)
                )
                FROM TiempoFuncionalidad t
                INNER JOIN  t.usuario usuario
                WHERE t.fechaEntrada IS NOT NULL
                  AND ( (:fechaInicio IS NULL OR :fechaFin IS NULL) OR (t.fechaEntrada BETWEEN :fechaInicio AND :fechaFin) )
                GROUP BY usuario.usuario, usuario.idUsuario, t.funcionalidad
                ORDER BY COUNT(t.idTiempoFuncionalidad) DESC
            """)
    List<UsuarioFuncionalidadDTO> findAllWithCountUser(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

}