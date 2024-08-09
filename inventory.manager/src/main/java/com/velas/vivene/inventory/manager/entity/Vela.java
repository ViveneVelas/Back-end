package com.velas.vivene.inventory.manager.entity;

import com.velas.vivene.inventory.manager.commons.Tamanho;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Vela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String tipo;
    private String aroma;
    private Tamanho tamanho;
    private Double preco;
    private LocalDate dtValidade;

}
