package com.velas.vivene.inventory.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.velas.vivene.inventory.manager.entity.Imagem;

import jakarta.transaction.Transactional;

public interface ImagemRepository extends JpaRepository<Imagem, Integer> {

    @Query("update Imagem i set i.referencia = ?2 where i.id = ?1")
    @Modifying
    @Transactional
    void updateReferenciaArquivoFoto(Integer id, String referenciaArquivoFoto);


    @Query("select i.referencia from Imagem i where i.id = ?1")
    String findReferenciaArquivoFotoById(Integer id);

}
