package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name="ultimo_acesso")
    private LocalDate ultimoAcesso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_login")
    private Login login;

}
