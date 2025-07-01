package com.revoktek.motivus.persistence.repositories;

import com.revoktek.motivus.persistence.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


}