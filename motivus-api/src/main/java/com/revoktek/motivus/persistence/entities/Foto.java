package com.revoktek.motivus.persistence.entities;

import jakarta.persistence.*;
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
@Table(name = "foto")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    private Long idFoto;

    @Lob
    @Column(name = "imagen", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @Column(name = "tipo", length = 10, nullable = false)
    private String tipo;

    @Column(name = "funcionalidad", length = 50)
    private String funcionalidad;

    @Column(name = "fecha_captura", nullable = false)
    private LocalDateTime fechaCaptura = LocalDateTime.now();

    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
