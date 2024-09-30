package com.velas.vivene.inventory.manager.dto.pedidovela;

import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoVela;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoVelaMapper {
    private final VelaRepository velaRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoVela toEntity(PedidoVelaRequestDto pedidoLoteRequestDto) {
        if (pedidoLoteRequestDto == null) {
            return null;
        }

        Vela vela = velaRepository.findById(pedidoLoteRequestDto.getVelaId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado com o id: " + pedidoLoteRequestDto.getVelaId()));

        Pedido pedido = pedidoRepository.findById(pedidoLoteRequestDto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + pedidoLoteRequestDto.getPedidoId()));

        PedidoVela pedidoLote = new PedidoVela();

        pedidoLote.setVela(vela);
        pedidoLote.setPedido(pedido);
        pedidoLote.setQuantidade(pedidoLoteRequestDto.getQuantidade());

        return pedidoLote;
    }

    public PedidoVelaResponseDto toResponseDto(PedidoVela pedidoLote) {
        if (pedidoLote == null) {
            return null;
        }

        PedidoVelaResponseDto pedidoLoteResponseDto = new PedidoVelaResponseDto();
        pedidoLoteResponseDto.setId(pedidoLote.getId());
        pedidoLoteResponseDto.setFkPedido(pedidoLote.getPedido().getId());
        pedidoLoteResponseDto.setFkVela(pedidoLote.getVela().getId());
        pedidoLoteResponseDto.setQtdVelas(pedidoLote.getQuantidade());

        return pedidoLoteResponseDto;
    }
}