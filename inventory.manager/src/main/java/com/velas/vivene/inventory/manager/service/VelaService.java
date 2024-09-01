package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.vela.VelaMapper;
import com.velas.vivene.inventory.manager.dto.vela.VelaRequestDto;
import com.velas.vivene.inventory.manager.dto.vela.VelaResponseDto;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaMapper;
import com.velas.vivene.inventory.manager.dto.velamaisvendida.VelaMaisVendidaResponse;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.entity.VelaMaisVendida;
import com.velas.vivene.inventory.manager.repository.VelaMaisVendidaRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VelaService {

    private final VelaRepository velaRepository;
    private final VelaMapper velaMapper;
    private final VelaMaisVendidaRepository velaMaisVendidaRepository;
    private final VelaMaisVendidaMapper velaMaisVendidaMapper;

    public VelaResponseDto createVela(VelaRequestDto velaRequestDTO) {
        Vela vela = velaMapper.toEntity(velaRequestDTO);
        vela = velaRepository.save(vela);
        return velaMapper.toResponseDTO(vela);
    }

    public VelaResponseDto updateVela(Integer id, VelaRequestDto velaRequestDTO) {
        Vela vela = velaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));

        vela.setNome(velaRequestDTO.getNome());
        vela.setTamanho(velaRequestDTO.getTamanho());
        vela.setPreco(velaRequestDTO.getPreco());

        vela = velaRepository.save(vela);
        return velaMapper.toResponseDTO(vela);
    }

    public void deleteVela(Integer id) {
        Vela vela = velaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));
        velaRepository.delete(vela);
    }

    public List<VelaResponseDto> getAllVelas() {
        return velaRepository.findAll()
                .stream()
                .map(velaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public VelaResponseDto getVelaById(Integer id) {
        Vela vela = velaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrada com o id: " + id));
        return velaMapper.toResponseDTO(vela);
    }

    public List<VelaMaisVendidaResponse> getVelaVendida() {
        List<VelaMaisVendida> velas = velaMaisVendidaRepository.findAll();
        List<VelaMaisVendidaResponse> velasResponse = new ArrayList<>();

        for (VelaMaisVendida v : velas) {
            VelaMaisVendidaResponse velaR = new VelaMaisVendidaResponse();
            velaR = velaMaisVendidaMapper.toResponseDTO(v);
            velasResponse.add(velaR);
        }

        return velasResponse;
    }
}
