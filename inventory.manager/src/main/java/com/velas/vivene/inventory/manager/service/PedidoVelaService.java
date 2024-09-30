package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaMapper;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaResponseDto;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoVela;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import com.velas.vivene.inventory.manager.repository.PedidoVelaRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoVelaService {

    private final PedidoVelaRepository pedidoVelaRepository;
    private final PedidoVelaMapper pedidoVelaMapper;
    private final PedidoRepository pedidoRepository;
    private final VelaRepository loteRepository;

    public PedidoVelaResponseDto createPedidoVela(PedidoVelaRequestDto pedidoVelaRequestDto) {
        PedidoVela pedidoVela = pedidoVelaMapper.toEntity(pedidoVelaRequestDto);
        PedidoVela pedidoVelaSalvo = pedidoVelaRepository.save(pedidoVela);
        return pedidoVelaMapper.toResponseDto(pedidoVelaSalvo);
    }

    public PedidoVelaResponseDto updatePedidoVela(Integer id, PedidoVelaRequestDto pedidoVelaRequestDto) {
        PedidoVela pedidoVela = pedidoVelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Vela não encontrado com o id: " + id));

        Pedido pedido = pedidoRepository.findById(pedidoVelaRequestDto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        Vela lote = loteRepository.findById(pedidoVelaRequestDto.getVelaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrado com o id: " + id));

        pedidoVela.setPedido(pedido);
        pedidoVela.setVela(lote);
        pedidoVela.setQuantidade(pedidoVelaRequestDto.getQuantidade());

        PedidoVela pedidoVelaSalvo = pedidoVelaRepository.save(pedidoVela);
        return pedidoVelaMapper.toResponseDto(pedidoVelaSalvo);
    }

    public void deletePedidoVela(Integer id) {
        PedidoVela pedidoVela = pedidoVelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Vela não encontrado com o id: " + id));
        pedidoVelaRepository.delete(pedidoVela);
    }

    public List<PedidoVelaResponseDto> getAllPedidoVela() {
        return pedidoVelaRepository.findAll()
                .stream()
                .map(pedidoVelaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public PedidoVelaResponseDto getPedidoVelaById(Integer id) {
        PedidoVela pedidoVela = pedidoVelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Vela não encontrado com o id: " + id));
        return pedidoVelaMapper.toResponseDto(pedidoVela);

    }
}
