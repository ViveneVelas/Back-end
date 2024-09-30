package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.meta.MetaMapper;
import com.velas.vivene.inventory.manager.dto.meta.MetaRequestDto;
import com.velas.vivene.inventory.manager.dto.meta.MetaResponseDto;
import com.velas.vivene.inventory.manager.dto.ultimametaseismeses.UltimaMetaSeisMesesMapper;
import com.velas.vivene.inventory.manager.dto.ultimametaseismeses.UltimaMetaSeisMesesResponse;
import com.velas.vivene.inventory.manager.entity.Meta;
import com.velas.vivene.inventory.manager.entity.view.UltimaMetaSeisMeses;
import com.velas.vivene.inventory.manager.repository.MetaRepository;
import com.velas.vivene.inventory.manager.repository.UltimaMetaSeisMesesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MetaService {

    private final MetaRepository metaRepository;
    private final MetaMapper metaMapper;
    private final UltimaMetaSeisMesesRepository ultimaMetaSeisMesesRepository;
    private final UltimaMetaSeisMesesMapper ultimaMetaSeisMesesMapper;

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

    public List<UltimaMetaSeisMesesResponse> getUltimaMetaSeisMeses() {
        List<UltimaMetaSeisMeses> metas = ultimaMetaSeisMesesRepository.findAll();
        List<UltimaMetaSeisMesesResponse> metasResponse = new ArrayList<>();

        for (UltimaMetaSeisMeses m : metas) {
            UltimaMetaSeisMesesResponse metaR = new UltimaMetaSeisMesesResponse();
            metaR = ultimaMetaSeisMesesMapper.toDto(m);
            metasResponse.add(metaR);
        }

        return metasResponse;
    }

    
}
