package com.velas.vivene.inventory.manager.dto.vela;

import com.velas.vivene.inventory.manager.entity.Vela;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
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
    private final ImagemService imagemService;
    private final S3BucketService s3BucketService;

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
        Imagem imagem = imagemRepository.findById(vela.getFkImagem())
        .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada com o id: " + vela.getFkImagem()));
        
        responseDTO.setReferencia(imagem.getReferencia());

        byte[] imagemBuscada = s3BucketService.buscarFoto(imagem.getId());

        responseDTO.setImagem(imagemBuscada);

        return responseDTO;
    }
}
