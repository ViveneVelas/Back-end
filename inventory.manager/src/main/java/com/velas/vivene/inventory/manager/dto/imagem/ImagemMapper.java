package com.velas.vivene.inventory.manager.dto.imagem;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.infraService.S3BucketService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImagemMapper {

    private final S3BucketService s3BucketService;


    public ImagemResponseDto toDto(Imagem imagem) throws IOException {
        if (imagem == null) {
            return null;
        }

        ImagemResponseDto dto = new ImagemResponseDto();

        dto.setId(imagem.getId());
        dto.setReferencia(imagem.getReferencia());
        byte[] imagemBuscada = s3BucketService.buscarFoto(imagem.getId());
        dto.setImagem(imagemBuscada);
        
        return dto;
    }

    public Imagem toEntity(ImagemRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Imagem imagem = new Imagem();
        imagem.setReferencia(dto.getReferencia());
        return imagem;
    }

    public Imagem toEntityFromResponse(ImagemResponseDto dto) {
        if (dto == null) {
            return null;
        }

        Imagem imagem = new Imagem();
        imagem.setId(dto.getId()); 
        imagem.setReferencia(dto.getReferencia());
        
        return imagem;
    }
}
