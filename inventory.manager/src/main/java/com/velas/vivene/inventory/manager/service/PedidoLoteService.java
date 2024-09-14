package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteMapper;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteResponseDto;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoLote;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoLoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoLoteService {

    private final PedidoLoteRepository pedidoLoteRepository;
    private final PedidoLoteMapper pedidoLoteMapper;
    private final PedidoRepository pedidoRepository;
    private final LoteRepository loteRepository;

    public PedidoLoteResponseDto createPedidoLote(PedidoLoteRequestDto pedidoLoteRequestDto) {
        PedidoLote pedidoLote = pedidoLoteMapper.toEntity(pedidoLoteRequestDto);
        PedidoLote pedidoLoteSalvo = pedidoLoteRepository.save(pedidoLote);
        return pedidoLoteMapper.toResponseDto(pedidoLoteSalvo);
    }

    public PedidoLoteResponseDto updatePedidoLote(Integer id, PedidoLoteRequestDto pedidoLoteRequestDto) {
        PedidoLote pedidoLote = pedidoLoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Lote não encontrado com o id: " + id));

        Pedido pedido = pedidoRepository.findById(pedidoLoteRequestDto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        Lote lote = loteRepository.findById(pedidoLoteRequestDto.getLoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + id));

        pedidoLote.setPedido(pedido);
        pedidoLote.setLote(lote);
        pedidoLote.setQuantidade(pedidoLoteRequestDto.getQuantidade());

        PedidoLote pedidoLoteSalvo = pedidoLoteRepository.save(pedidoLote);
        return pedidoLoteMapper.toResponseDto(pedidoLoteSalvo);
    }

    public void deletePedidoLote(Integer id) {
        PedidoLote pedidoLote = pedidoLoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Lote não encontrado com o id: " + id));
        pedidoLoteRepository.delete(pedidoLote);
    }

    public List<PedidoLoteResponseDto> getAllPedidoLote() {
        return pedidoLoteRepository.findAll()
                .stream()
                .map(pedidoLoteMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public PedidoLoteResponseDto getPedidoLoteById(Integer id) {
        PedidoLote pedidoLote = pedidoLoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Lote não encontrado com o id: " + id));
        return pedidoLoteMapper.toResponseDto(pedidoLote);

    }
}
