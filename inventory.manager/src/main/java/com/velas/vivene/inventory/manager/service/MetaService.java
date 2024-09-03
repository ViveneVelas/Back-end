package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.meta.MetaMapper;
import com.velas.vivene.inventory.manager.dto.meta.MetaRequestDto;
import com.velas.vivene.inventory.manager.dto.meta.MetaResponseDto;
import com.velas.vivene.inventory.manager.entity.Meta;
import com.velas.vivene.inventory.manager.repository.MetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final MetaMapper metaMapper;

    public MetaResponseDto createMeta(MetaRequestDto metaRequestDto) {
        Meta meta = metaMapper.toEntity(metaRequestDto);
        meta = metaRepository.save(meta);
        return metaMapper.toDto(meta);
    }

    public MetaResponseDto updateMeta(Integer id, MetaRequestDto metaRequestDto) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com o id: " + id));

        meta.setDataInicio(metaRequestDto.getDataInicio());
        meta.setDataFinal(metaRequestDto.getDataFinal());
        meta.setQtdVendas(metaRequestDto.getQtdVendas());

        meta = metaRepository.save(meta);
        return metaMapper.toDto(meta);
    }

    public void deletarMeta(Integer id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com o id: " + id));
        metaRepository.delete(meta);
    }

    public List<MetaResponseDto> getAllMetas() {
        return metaRepository.findAll()
                .stream()
                .map(metaMapper::toDto)
                .collect(Collectors.toList());
    }

    public MetaResponseDto getMetaById(Integer id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada com o id: " + id));
        return metaMapper.toDto(meta);
    }

    
}
