package com.velas.vivene.inventory.manager.dto.lote;

import com.velas.vivene.inventory.manager.dto.vela.VelaMapper;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoteMapper {
    private final VelaRepository velaRepository;
    private final VelaMapper velaMapper;

    public Lote toEntity(LoteRequestDto dto) {
        Lote lote = new Lote();
        lote.setQuantidade(dto.getQuantidade());
        lote.setDataFabricacao(dto.getDataFabricacao());
        lote.setDataValidade(dto.getDataValidade());
        lote.setLocalizacao(dto.getLocalizacao());
        return lote;
    }

    public LoteResponseDto toResponseDTO(Lote entity) throws IOException {
        LoteResponseDto dto = new LoteResponseDto();
        dto.setId(entity.getId());
        dto.setQuantidade(entity.getQuantidade());
        dto.setDataFabricacao(entity.getDataFabricacao());
        dto.setDataValidade(entity.getDataValidade());
        dto.setLocalizacao(entity.getLocalizacao());
        dto.setQrCode(entity.getCodigoQrCode());
        dto.setVela(velaMapper.toResponseDTO(entity.getVela()));

        return dto;
    }

    public LoteResponseDto toResponseDTO(Lote entity, byte[] arqPng) {
        LoteResponseDto dto = new LoteResponseDto();
        dto.setId(entity.getId());
        dto.setQuantidade(entity.getQuantidade());
        dto.setDataFabricacao(entity.getDataFabricacao());
        dto.setDataValidade(entity.getDataValidade());
        dto.setLocalizacao(entity.getLocalizacao());
        Integer id = entity.getVela().getId();
        dto.setFkVela(id);
        dto.setNomeArq(entity.getNomeQrCode());
        dto.setPngQrcode(arqPng);

        return dto;
    }
}