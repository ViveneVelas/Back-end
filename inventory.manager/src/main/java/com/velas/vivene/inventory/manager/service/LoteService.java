package com.velas.vivene.inventory.manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.lote.LoteRequestDto;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximoDoVencimentoResponse;
import com.velas.vivene.inventory.manager.dto.lotesproximodovencimento.LotesProximosDoVencimentoMapper;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.entity.view.LotesProximoDoVencimento;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.LotesProximoDoVencimentoRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

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

        if (loteRequestDTO == null) {
            throw new ValidationException("Os dados do lote são obrigatórios.");
        }

        try {
            Lote lote = loteMapper.toEntity(loteRequestDTO);
            lote.setVela(vela);
            lote = loteRepository.save(lote);
            return loteMapper.toResponseDTO(lote);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar o lote.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar lote " + ex);
        }
    }

    public List<LoteResponseDto> listarLotes() {
        List<LoteResponseDto> lotes = loteRepository.findAll().stream()
                .map(loteMapper::toResponseDTO)
                .toList();

        if (lotes.isEmpty()) {
            throw new NoContentException("Não existe nenhum lote no banco de dados");
        }

        return lotes;
    }

    public LoteResponseDto obterLotePorId(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));
        return loteMapper.toResponseDTO(lote);
    }

    public LoteResponseDto updateLote(Integer id, LoteRequestDto loteRequestDTO) {

        if (loteRequestDTO == null) {
            throw new ValidationException("Os dados do lote são obrigatórios.");
        }

        try {
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
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Erro de integridade ao atualizar o lote: " + ex.getMessage());
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar o lote " + ex);
        }
    }

    public void excluirLote(Integer id) {
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));
        loteRepository.delete(lote);
    }

    public List<LotesProximoDoVencimentoResponse> getLotesVencimento() {
        try {
            List<LotesProximoDoVencimento> lotes = lotesProximoDoVencimentoRepository.findAll();
            List<LotesProximoDoVencimentoResponse> lotesResponse = new ArrayList<>();

            if (lotes.isEmpty()) {
                throw new NoContentException("Não existe nenhum lote no banco de dados");
            }
    
            for (LotesProximoDoVencimento l : lotes) {
                LotesProximoDoVencimentoResponse lotesR = new LotesProximoDoVencimentoResponse();
                lotesR = lotesProximosDoVencimentoMapper.toResponseDto(l);
                lotesResponse.add(lotesR);
            }
    
            return lotesResponse;
        } catch (NoContentException ex) {
            throw new NoContentException("Nenhum lote próximo do vencimento foi encontrado.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao buscar os lotes próximos do vencimento.");
        }
    }
    
}
