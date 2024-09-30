package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximoDoVencimentoResponse;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximosDoVencimentoMapper;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.view.LotesProximoDoVencimento;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.LotesProximoDoVencimentoRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final VelaRepository velaRepository;
    private final LotesProximoDoVencimentoRepository lotesProximoDoVencimentoRepository;
    private final LotesProximosDoVencimentoMapper lotesProximosDoVencimentoMapper;

    public LoteResponseDto criarLote(LoteRequestDto loteRequestDTO) {
        Vela vela = velaRepository.findById(loteRequestDTO.getFkVela())
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + loteRequestDTO.getFkVela()));

        Lote lote = loteMapper.toEntity(loteRequestDTO);
        lote.setVela(vela);
        lote = loteRepository.save(lote);

        return loteMapper.toResponseDTO(lote);
    }

    public List<LoteResponseDto> listarLotes() {
        return loteRepository.findAll().stream()
                .map(loteMapper::toResponseDTO)
                .toList();
    }


    public LoteResponseDto obterLotePorId(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));
        return loteMapper.toResponseDTO(lote);
    }

    public LoteResponseDto updateLote(Integer id, LoteRequestDto loteRequestDTO) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));

        lote.setQuantidade(loteRequestDTO.getQuantidade());
        lote.setDataFabricacao(loteRequestDTO.getDataFabricacao());
        lote.setDataValidade(loteRequestDTO.getDataValidade());
        lote.setLocalizacao(loteRequestDTO.getLocalizacao());

        Vela vela = velaRepository.findById(loteRequestDTO.getFkVela())
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + loteRequestDTO.getFkVela()));
        lote.setVela(vela);

        lote = loteRepository.save(lote);
        return loteMapper.toResponseDTO(lote);
    }

    public void excluirLote(Integer id) {
        loteRepository.deleteById(id);
    }

    public List<LotesProximoDoVencimentoResponse> getLotesVencimento() {
        List<LotesProximoDoVencimento> lotes = lotesProximoDoVencimentoRepository.findAll();
        List<LotesProximoDoVencimentoResponse> lotesResponse = new ArrayList<>();

        for (LotesProximoDoVencimento l : lotes) {
            LotesProximoDoVencimentoResponse lotesR = new LotesProximoDoVencimentoResponse();
            lotesR = lotesProximosDoVencimentoMapper.toResponseDto(l);
            lotesResponse.add(lotesR);
        }

        return lotesResponse;
    }
}