package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    public LoteResponseDto criarLote(LoteRequestDto loteRequestDTO) {
        Lote lote = loteMapper.toEntity(loteRequestDTO);
        lote = loteRepository.save(lote);
        return loteMapper.toResponseDTO(lote);
    }

    public List<LoteResponseDto> listarLotes() {
        return loteRepository.findAll().stream()
                .map(loteMapper::toResponseDTO)
                .toList();
    }

    public Optional<LoteResponseDto> obterLotePorId(Integer id) {
        return loteRepository.findById(id)
                .map(loteMapper::toResponseDTO);
    }

    public void excluirLote(Integer id) {
        loteRepository.deleteById(id);
    }
}