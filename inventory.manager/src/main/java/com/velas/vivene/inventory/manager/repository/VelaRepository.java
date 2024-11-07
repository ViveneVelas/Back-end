package com.velas.vivene.inventory.manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velas.vivene.inventory.manager.entity.Vela;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VelaRepository extends JpaRepository<Vela, Integer> {
    List<Vela> findAllByNomeIn(List<String> nomes);

    @Query("SELECT v FROM Vela v ORDER BY " +
            "CASE WHEN :orderBy = 'nome' THEN v.nome END ASC, " +
            "CASE WHEN :orderBy = 'id' THEN v.id END ASC")
    List<Vela> findAllOrderBy(@Param("orderBy") String orderBy);

    @Query("SELECT v FROM Vela v WHERE LOWER(v.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Vela> findByNameIgnoreCase(@Param("nome") String nome);
}
