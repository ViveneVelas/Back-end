package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
