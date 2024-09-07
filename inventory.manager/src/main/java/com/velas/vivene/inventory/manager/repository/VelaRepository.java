package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Vela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VelaRepository extends JpaRepository<Vela, Integer> {
    Optional<Vela> findByNome(String nome);
}
