package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.login.id = :loginId")
    Optional<Usuario> findByLoginId(@Param("loginId") Integer loginId);
}
