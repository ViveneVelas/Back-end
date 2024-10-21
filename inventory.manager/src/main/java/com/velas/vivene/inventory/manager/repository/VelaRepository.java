package com.velas.vivene.inventory.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velas.vivene.inventory.manager.entity.Vela;

public interface VelaRepository extends JpaRepository<Vela, Integer> {
    List<Vela> findAllByNomeIn(List<String> nomes);
}
