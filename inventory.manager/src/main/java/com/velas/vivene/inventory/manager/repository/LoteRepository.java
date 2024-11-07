package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.Vela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {

    List<Lote> findAllByLocalizacao(Integer integer);

    @Query("SELECT l FROM Lote l WHERE LOWER(l.vela.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND l.localizacao = 0")
    List<Lote> findByNameIgnoreCaseCasa(@Param("nome") String nome);

    @Query("SELECT l FROM Lote l WHERE LOWER(l.vela.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND l.localizacao = 1")
    List<Lote> findByNameIgnoreCaseEstudio(@Param("nome") String nome);
}
