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
@Table(name = "resultado_evento")
public class ResultadoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado_evento")
    private Long idResultadoEvento;

    @Column(unique = true, length = 30)
    private String clave;

    @Column(length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "resultadoEvento")
    private List<EventoBiometrico> eventos;

}
