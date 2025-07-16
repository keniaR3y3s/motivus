package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.persistence.entities.Foto;
import com.revoktek.motivus.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Long> {

    List<Foto> findAllByTipoAndActivoAndUsuario(String tipo, Boolean activo, Usuario usuario);
}