package com.revoktek.motivus.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evento_biometrico")
public class EventoBiometrico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento_biometrico")
    private Long idEventoBiometrico;

    @Column(name = "id_transacional", length = 500)
    private String idTransacional;

    @Column(name = "fecha_hora_dia", nullable = false)
    private LocalDateTime fechaHoraDia;

    @Column
    private BigDecimal porcentaje;

    @Column(name = "version_android_dispositivo", length = 20)
    private String versionAndroidDispositivo;

    @Column(length = 100)
    private String dispositivo;

    @Column(length = 50)
    private String gps;

    @Column(name = "resultado_descripcion", length = 10000)
    private String resultadoDescripcion;

    @Column(name = "entidad_federativa")
    private String entidadFederativa;

    private Integer tiempoRespuestaMs;
    private Integer tiempoProcesoMs;

    @Column(length = 20)
    private String ovalAlineado;

    private Boolean validadoPorTflite;
    private Boolean offlineEvento;

    private Boolean sincronizado = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tipo_evento_id", nullable = false)
    private TipoEvento tipoEvento;

    @ManyToOne
    @JoinColumn(name = "resultado_evento_id", nullable = false)
    private ResultadoEvento resultadoEvento;

    @OneToMany(mappedBy = "eventoBiometrico")
    private List<TiempoFuncionalidad> tiempos;

}
