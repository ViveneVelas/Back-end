package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Lotes")
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_vela", nullable = false)
    private Vela vela;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "localizacao")
    private Integer localizacao;

    @Column(name = "codigo_qr_code")
    private String codigoQrCode;

}
