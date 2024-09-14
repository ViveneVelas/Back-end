package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.venda.VendaMapper;
import com.velas.vivene.inventory.manager.dto.venda.VendaRequestDto;
import com.velas.vivene.inventory.manager.dto.venda.VendaResponseDto;
import com.velas.vivene.inventory.manager.dto.vendasdasemana.VendasDaSemanaMapper;
import com.velas.vivene.inventory.manager.dto.vendasdasemana.VendasDaSemanaResponseDto;
import com.velas.vivene.inventory.manager.entity.QtdVendasDaSemana;
import com.velas.vivene.inventory.manager.entity.Venda;
import com.velas.vivene.inventory.manager.repository.QtdVendasDaSemanaRepository;
import com.velas.vivene.inventory.manager.repository.VendaRepository;
import com.velas.vivene.inventory.manager.repository.VendasDaSemanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;
    private final VendasDaSemanaRepository vendasDaSemanaRepository;
    private final VendasDaSemanaMapper vendasDaSemanaMapper;
    private final QtdVendasDaSemanaRepository qtdVendasDaSemanaRepository;

    public VendaResponseDto createVenda(VendaRequestDto vendaRequestDTO) {
        Venda venda = vendaMapper.toEntity(vendaRequestDTO);
        venda = vendaRepository.save(venda);
        return vendaMapper.toResponseDTO(venda);
    }

    public VendaResponseDto updateVenda(Integer id, VendaRequestDto vendaRequestDTO) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o id: " + id));

        venda.setMetodoPag(vendaRequestDTO.getMetodoPag());
        venda = vendaRepository.save(venda);

        return vendaMapper.toResponseDTO(venda);
    }

    public void deleteVenda(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o id: " + id));
        vendaRepository.delete(venda);
    }

    public List<VendaResponseDto> getAllVendas() {
        return vendaRepository.findAll()
                .stream()
                .map(vendaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public VendaResponseDto getVendaById(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com o id: " + id));
        return vendaMapper.toResponseDTO(venda);

    }

    public List<VendasDaSemanaResponseDto> getVendaSemanal() {
        return vendasDaSemanaRepository.findAll()
                .stream()
                .map(vendasDaSemanaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Integer getQtdVendaSemanal() {
        List<QtdVendasDaSemana> qtd = qtdVendasDaSemanaRepository.findAll();
        return qtd.get(0).getQtd();
    }
}