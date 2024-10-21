package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {

    List<Lote> findAllByLocalizacao(Integer integer);
}
