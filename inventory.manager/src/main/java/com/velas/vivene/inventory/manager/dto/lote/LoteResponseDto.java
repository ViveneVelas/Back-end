package com.velas.vivene.inventory.manager.dto.lote;

import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class LoteResponseDto {

    private Integer id;
    private VelaResponseDto vela;
    private Integer quantidade;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private Integer localizacao;
    private String qrCode;
    private String nomeArq;
    private byte[] pngQrcode;
}