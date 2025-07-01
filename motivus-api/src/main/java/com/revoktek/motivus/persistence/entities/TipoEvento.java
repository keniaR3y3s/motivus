package com.revoktek.motivus.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_evento")
public class TipoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_evento")
    private Long idTipoEvento;

    @Column(unique = true, length = 50)
    private String clave;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    @Column(length = 100)
    private String metodo;

    @OneToMany(mappedBy = "tipoEvento")
    private List<EventoBiometrico> eventos;

}
