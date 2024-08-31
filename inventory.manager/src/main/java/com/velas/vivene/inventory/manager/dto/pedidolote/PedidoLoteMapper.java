package com.velas.vivene.inventory.manager.dto.pedidolote;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoLote;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoLoteMapper {
    private final LoteRepository loteRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoLote toEntity(PedidoLoteRequestDto pedidoLoteRequestDto) {
        if (pedidoLoteRequestDto == null) {
            return null;
        }

        Lote lote = loteRepository.findById(pedidoLoteRequestDto.getLoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + pedidoLoteRequestDto.getLoteId()));

        Pedido pedido = pedidoRepository.findById(pedidoLoteRequestDto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + pedidoLoteRequestDto.getPedidoId()));

        PedidoLote pedidoLote = new PedidoLote();

        pedidoLote.setLote(lote);
        pedidoLote.setPedido(pedido);
        pedidoLote.setQuantidade(pedidoLoteRequestDto.getQuantidade());

        return pedidoLote;
    }

    public PedidoLoteResponseDto toResponseDto(PedidoLote pedidoLote) {
        if (pedidoLote == null) {
            return null;
        }

        PedidoLoteResponseDto pedidoLoteResponseDto = new PedidoLoteResponseDto();
        pedidoLoteResponseDto.setId(pedidoLote.getId());
        pedidoLoteResponseDto.setFkPedido(pedidoLote.getPedido().getId());
        pedidoLoteResponseDto.setFkLote(pedidoLote.getLote().getId());

        return pedidoLoteResponseDto;
    }
}