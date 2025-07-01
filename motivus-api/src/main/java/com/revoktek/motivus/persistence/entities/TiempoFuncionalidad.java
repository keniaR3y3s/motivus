package com.revoktek.motivus.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tiempo_funcionalidad")
public class TiempoFuncionalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tiempo_funcionalidad")
    private Long idTiempoFuncionalidad;

    @Column(length = 100)
    private String funcionalidad;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    // Campos generados, opcionales si no necesitas consultar directamente
    @Column(name = "duracion_horas", insertable = false, updatable = false)
    private Integer duracionHoras;

    @Column(name = "duracion_minutos", insertable = false, updatable = false)
    private Integer duracionMinutos;

    @Column(name = "duracion_solo_segundos", insertable = false, updatable = false)
    private Integer duracionSoloSegundos;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_biometrico_id", nullable = false)
    private EventoBiometrico eventoBiometrico;

}
