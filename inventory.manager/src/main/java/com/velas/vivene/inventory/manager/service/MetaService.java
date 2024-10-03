package com.velas.vivene.inventory.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.meta.MetaMapper;
import com.velas.vivene.inventory.manager.dto.meta.MetaRequestDto;
import com.velas.vivene.inventory.manager.dto.meta.MetaResponseDto;
import com.velas.vivene.inventory.manager.dto.ultimametaseismeses.UltimaMetaSeisMesesMapper;
import com.velas.vivene.inventory.manager.dto.ultimametaseismeses.UltimaMetaSeisMesesResponse;
import com.velas.vivene.inventory.manager.entity.Meta;
import com.velas.vivene.inventory.manager.entity.view.UltimaMetaSeisMeses;
import com.velas.vivene.inventory.manager.repository.MetaRepository;
import com.velas.vivene.inventory.manager.repository.UltimaMetaSeisMesesRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final MetaMapper metaMapper;
    private final UltimaMetaSeisMesesRepository ultimaMetaSeisMesesRepository;
    private final UltimaMetaSeisMesesMapper ultimaMetaSeisMesesMapper;

    public MetaResponseDto createMeta(MetaRequestDto metaRequestDto) {

        if (metaRequestDto == null) {
            throw new ValidationException("Os dados da meta são obrigatórios.");
        }

        try {
            Meta meta = metaMapper.toEntity(metaRequestDto);
            meta = metaRepository.save(meta);
            return metaMapper.toDto(meta);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar a meta.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar meta.");
        }
    }

    public MetaResponseDto updateMeta(Integer id, MetaRequestDto metaRequestDto) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com o id: " + id));

        if (metaRequestDto == null) {
            throw new ValidationException("Os dados da meta são obrigatórios.");
        }

        try {
            meta.setDataInicio(metaRequestDto.getDataInicio());
            meta.setDataFinal(metaRequestDto.getDataFinal());
            meta.setQtdVendas(metaRequestDto.getQtdVendas());

            meta = metaRepository.save(meta);
            return metaMapper.toDto(meta);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar a meta.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar meta.");
        }
    }

    public void deletarMeta(Integer id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com o id: " + id));
        metaRepository.delete(meta);
    }

    public List<MetaResponseDto> getAllMetas() {
        List<MetaResponseDto> metas = metaRepository.findAll()
                .stream()
                .map(metaMapper::toDto)
                .collect(Collectors.toList());

        if (metas.isEmpty()) {
            throw new NoContentException("Não existe nenhum cliente no banco de dados");
        }

        return metas;
    }

    public MetaResponseDto getMetaById(Integer id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com o id: " + id));
        return metaMapper.toDto(meta);
    }

    public List<UltimaMetaSeisMesesResponse> getUltimaMetaSeisMeses() {
        List<UltimaMetaSeisMeses> metas = ultimaMetaSeisMesesRepository.findAll();

        if (metas.isEmpty()) {
            throw new NoContentException("Nenhum cliente com mais compras encontrado.");
        }

        List<UltimaMetaSeisMesesResponse> metasResponse = new ArrayList<>();

        for (UltimaMetaSeisMeses m : metas) {
            UltimaMetaSeisMesesResponse metaR = new UltimaMetaSeisMesesResponse();
            metaR = ultimaMetaSeisMesesMapper.toDto(m);
            metasResponse.add(metaR);
        }

        return metasResponse;
    }

}
