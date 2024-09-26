package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Vela;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface VelaRepository extends JpaRepository<Vela, Integer> {
    List<Vela> findAllByNomeIn(List<String> nomes);
}
