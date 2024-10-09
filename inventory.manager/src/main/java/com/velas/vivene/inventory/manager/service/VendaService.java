package com.velas.vivene.inventory.manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.venda.VendaMapper;
import com.velas.vivene.inventory.manager.dto.venda.VendaRequestDto;
import com.velas.vivene.inventory.manager.dto.venda.VendaResponseDto;
import com.velas.vivene.inventory.manager.dto.vendasdasemana.VendasDaSemanaMapper;
import com.velas.vivene.inventory.manager.dto.vendasdasemana.VendasDaSemanaResponseDto;
import com.velas.vivene.inventory.manager.entity.Venda;
import com.velas.vivene.inventory.manager.entity.view.QtdVendasDaSemana;
import com.velas.vivene.inventory.manager.repository.QtdVendasDaSemanaRepository;
import com.velas.vivene.inventory.manager.repository.VendaRepository;
import com.velas.vivene.inventory.manager.repository.VendasDaSemanaRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;
    private final VendasDaSemanaRepository vendasDaSemanaRepository;
    private final VendasDaSemanaMapper vendasDaSemanaMapper;
    private final QtdVendasDaSemanaRepository qtdVendasDaSemanaRepository;

    public VendaResponseDto createVenda(VendaRequestDto vendaRequestDTO) {
        if (vendaRequestDTO == null) {
            throw new ValidationException("Os dados da venda são obrigatórios.");
        }

        try {
            Venda venda = vendaMapper.toEntity(vendaRequestDTO);
            venda = vendaRepository.save(venda);
            return vendaMapper.toResponseDTO(venda);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar a venda.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar venda " + ex);
        }
    }

    public VendaResponseDto updateVenda(Integer id, VendaRequestDto vendaRequestDTO) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o id: " + id));

        if (vendaRequestDTO == null) {
            throw new ValidationException("Os dados da venda são obrigatórios.");
        }

        try {
            venda.setMetodoPag(vendaRequestDTO.getMetodoPag());
            venda = vendaRepository.save(venda);

            return vendaMapper.toResponseDTO(venda);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar a venda.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar venda " + ex);
        }
    }

    public void deleteVenda(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o id: " + id));
        vendaRepository.delete(venda);
    }

    public List<VendaResponseDto> getAllVendas() {
        List<VendaResponseDto> vendas = vendaRepository.findAll()
                .stream()
                .map(vendaMapper::toResponseDTO)
                .collect(Collectors.toList());

        if (vendas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma venda no banco de dados");
        }

        return vendas;
    }

    public VendaResponseDto getVendaById(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o id: " + id));
        return vendaMapper.toResponseDTO(venda);

    }

    public List<VendasDaSemanaResponseDto> getVendaSemanal() {
        List<VendasDaSemanaResponseDto> vendas = vendasDaSemanaRepository.findAll()
                .stream()
                .map(vendasDaSemanaMapper::toResponseDTO)
                .collect(Collectors.toList());

        if (vendas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma venda no banco de dados");
        }

        return vendas;
    }

    public Integer getQtdVendaSemanal() {
        List<QtdVendasDaSemana> qtd = qtdVendasDaSemanaRepository.findAll();
        if (qtd.isEmpty()) {
            throw new NoContentException("Não existe nenhuma quantidade de vendas no banco de dados");
        }
        return qtd.get(0).getQtd();
    }
}
