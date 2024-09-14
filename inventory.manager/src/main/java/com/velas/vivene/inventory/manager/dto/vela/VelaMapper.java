package com.velas.vivene.inventory.manager.dto.vela;

import com.velas.vivene.inventory.manager.entity.Vela;
import org.springframework.stereotype.Component;

@Component
public class VelaMapper {

    public Vela toEntity(VelaRequestDto velaRequestDto) {
        if (velaRequestDto == null) {
            return null;
        }

        Vela vela = new Vela();
        vela.setNome(velaRequestDto.getNome());
        vela.setTamanho(velaRequestDto.getTamanho());
        vela.setPreco(velaRequestDto.getPreco());
        vela.setDescricao(velaRequestDto.getDescricao());

        return vela;
    }

    public VelaResponseDto toResponseDTO(Vela vela) {
        if (vela == null) {
            return null;
        }

        VelaResponseDto responseDTO = new VelaResponseDto();
        responseDTO.setId(vela.getId());
        responseDTO.setNome(vela.getNome());
        responseDTO.setTamanho(vela.getTamanho());
        responseDTO.setPreco(vela.getPreco());
        responseDTO.setDescricao(vela.getDescricao());

        return responseDTO;
    }
}
