package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login>findByEmailAndSenha(String email, String senha);
}
