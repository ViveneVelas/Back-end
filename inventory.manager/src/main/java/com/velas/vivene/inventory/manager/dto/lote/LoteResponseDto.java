package com.velas.vivene.inventory.manager.dto.lote;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class LoteResponseDto {

    private Integer id;
    private Integer fkVela;
    private Integer quantidade;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private Integer localizacao;
    private String nomeArq;
    private byte[] pngQrcode;
}