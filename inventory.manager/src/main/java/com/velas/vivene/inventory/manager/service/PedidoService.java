package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.Pagamento;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.lote.LoteResponseDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoMapper;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteRequestDto;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesMapper;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesResponse;
import com.velas.vivene.inventory.manager.entity.*;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import com.velas.vivene.inventory.manager.repository.QuantidadeVendasSeisMesesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final LoteRepository loteRepository;
    private final PedidoLoteService pedidoLoteService;
    private final QuantidadeVendasSeisMesesRepository quantidadeVendasSeisMesesRepository;
    private final QuantidadeVendasSeisMesesMapper quantidadeVendasSeisMesesMapper;
    private final LoteMapper loteMapper;
    private final LoteService loteService;

    public PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequest) {
        PedidoLoteRequestDto pedidoLote = new PedidoLoteRequestDto();

        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);
        Pedido pedidoSave = pedidoRepository.save(pedido);

        pedidoLote.setPedidoId(pedidoSave.getId());
        pedidoLote.setLoteId(pedidoRequest.getLoteId());
        pedidoLote.setQuantidade(pedidoRequest.getQtdVelas());

        pedidoLoteService.createPedidoLote(pedidoLote);

        return pedidoMapper.toResponseDTO(pedidoSave);
    }


    public PedidoResponseDto updatePedido(Integer id, PedidoRequestDto pedidoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));

        pedido.setDtPedido(pedidoRequestDTO.getDtPedido());
        pedido.setDescricao(pedidoRequestDTO.getDescricao());
        pedido.setTipoEntrega(pedidoRequestDTO.getTipoEntrega());
        pedido.setStatus(pedidoRequestDTO.getStatus());

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(updatedPedido);
    }

    public PedidoResponseDto finalizaPedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));

        pedido.setStatus(Pagamento.FINALIADO.getDescricao());

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(updatedPedido);
    }

    public void deletePedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        pedidoRepository.delete(pedido);
    }

    public List<PedidoResponseDto> getAllPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public PedidoResponseDto getPedidoById(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        return pedidoMapper.toResponseDTO(pedido);
    }

    public List<QuantidadeVendasSeisMesesResponse> getQuantidadeVendasSeisMeses() {
        List<QuantidadeVendasSeisMeses> vendas = quantidadeVendasSeisMesesRepository.findAll();
        List<QuantidadeVendasSeisMesesResponse> vendasResponse = new ArrayList<>();

        for (QuantidadeVendasSeisMeses v : vendas) {
            QuantidadeVendasSeisMesesResponse vendaR = new QuantidadeVendasSeisMesesResponse();
            vendaR = quantidadeVendasSeisMesesMapper.toDto(v);
            vendasResponse.add(vendaR);
        }

        return vendasResponse;
    }

}