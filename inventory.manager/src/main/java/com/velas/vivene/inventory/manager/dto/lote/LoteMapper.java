package com.velas.vivene.inventory.manager.dto.lote;

import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoteMapper {
    private final VelaRepository velaRepository;

    public Lote toEntity(LoteRequestDto dto) {
        Lote lote = new Lote();
        lote.setQuantidade(dto.getQuantidade());
        lote.setDataFabricacao(dto.getDataFabricacao());
        lote.setDataValidade(dto.getDataValidade());
        lote.setLocalizacao(dto.getLocalizacao());
        return lote;
    }

    public LoteResponseDto toResponseDTO(Lote entity) {
        LoteResponseDto dto = new LoteResponseDto();
        dto.setId(entity.getId());
        dto.setQuantidade(entity.getQuantidade());
        dto.setDataFabricacao(entity.getDataFabricacao());
        dto.setDataValidade(entity.getDataValidade());
        dto.setLocalizacao(entity.getLocalizacao());
        dto.setQrCode(entity.getCodigoQrCode());
        Integer id = entity.getVela().getId();
        dto.setFkVela(id);

        return dto;
    }
}