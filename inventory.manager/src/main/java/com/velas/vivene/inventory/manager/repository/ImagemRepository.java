package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Imagem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {

    @Query("update Imagem i set i.referencia = ?2 where i.id = ?1")
    @Modifying
    @Transactional
    void updateReferenciaArquivoFoto(Long id, String referenciaArquivoFoto);


    @Query("select i.referencia from Imagem i where i.id = ?1")
    String findReferenciaArquivoFotoById(Long id);

}
