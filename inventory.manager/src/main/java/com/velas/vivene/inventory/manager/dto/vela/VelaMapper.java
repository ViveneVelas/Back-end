package com.velas.vivene.inventory.manager.dto.vela;

import com.velas.vivene.inventory.manager.entity.Vela;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.infraService.S3BucketService;
import com.velas.vivene.inventory.manager.repository.ImagemRepository;
import com.velas.vivene.inventory.manager.service.ImagemService;

@Component
@RequiredArgsConstructor
public class VelaMapper {

    private final ImagemRepository imagemRepository;
    private final S3BucketService s3BucketService;
    private final ImagemService imagemService;
    private final S3BucketService s3;

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

    public byte[] resposta(Integer id) throws IOException {
        return s3.buscarFoto(Integer.valueOf(id));
    }

    public VelaResponseDto toResponseDTO(Vela vela) throws IOException {
        if (vela == null) {
            return null;
        }

        VelaResponseDto responseDTO = new VelaResponseDto();
        responseDTO.setId(vela.getId());
        responseDTO.setNome(vela.getNome());
        responseDTO.setTamanho(vela.getTamanho());
        responseDTO.setPreco(vela.getPreco());
        responseDTO.setDescricao(vela.getDescricao());

        byte[] res = resposta(vela.getFkImagem());
        responseDTO.setImagem(Base64.getEncoder().encodeToString(res));
        return responseDTO;
    }
}
