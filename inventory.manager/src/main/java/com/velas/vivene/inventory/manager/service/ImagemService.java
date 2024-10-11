package com.velas.vivene.inventory.manager.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.imagem.ImagemMapper;
import com.velas.vivene.inventory.manager.dto.imagem.ImagemRequestDto;
import com.velas.vivene.inventory.manager.dto.imagem.ImagemResponseDto;
import com.velas.vivene.inventory.manager.entity.Imagem;
import com.velas.vivene.inventory.manager.infraService.S3BucketService;
import com.velas.vivene.inventory.manager.repository.ImagemRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private final ImagemMapper imagemMapper;
    private final S3BucketService s3BucketService;

    public ImagemResponseDto createImagem(ImagemRequestDto dto) {
        if (dto == null || dto.getImagem() == null) {
            throw new ValidationException("Os dados da imagem são obrigatórios.");
        }

        try {
            Imagem imagem = imagemMapper.toEntity(dto);
            imagem = imagemRepository.save(imagem);
            s3BucketService.salvarFoto(imagem.getId(), dto.getImagem());
            return imagemMapper.toDto(imagem);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar a imagem.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar imagem: " + ex.getMessage());
        }
    }

    public ImagemResponseDto updateImagem(Integer id, ImagemRequestDto dto) {
        Imagem imagem = imagemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada com o id: " + id));

        if (dto == null || dto.getImagem() == null) {
            throw new ValidationException("Os dados da imagem são obrigatórios.");
        }

        try {
            imagem.setReferencia(dto.getReferencia());
            imagemRepository.save(imagem);  
            
            s3BucketService.deletarFoto(id);
            s3BucketService.salvarFoto(id, dto.getImagem());

            return imagemMapper.toDto(imagem);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar a imagem.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar a imagem: " + ex.getMessage());
        }
    }

    public void deleteImagem(Integer id) {
        Imagem imagem = imagemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada com o id: " + id));

        try {
            s3BucketService.deletarFoto(id);
            imagemRepository.delete(imagem);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao deletar a imagem.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao deletar a imagem: " + ex.getMessage());
        }
    }
}
