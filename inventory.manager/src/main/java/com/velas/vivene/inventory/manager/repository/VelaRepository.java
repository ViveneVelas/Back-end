package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Vela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VelaRepository extends JpaRepository<Vela, Integer> {
    List<Vela> findAllByNomeIn(List<String> nomes);
}
