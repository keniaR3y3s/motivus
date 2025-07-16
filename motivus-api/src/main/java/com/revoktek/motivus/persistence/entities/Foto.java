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

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "tipo")
    private String tipo;


    @Column(name = "fecha_captura")
    private LocalDateTime fechaCaptura = LocalDateTime.now();

    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
