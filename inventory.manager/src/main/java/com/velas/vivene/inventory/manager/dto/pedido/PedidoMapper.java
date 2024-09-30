package com.velas.vivene.inventory.manager.dto.pedido;

import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaResponseDto;
import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoVela;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoVelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PedidoMapper {

    private final ClienteRepository clienteRepository;
    private final PedidoVelaRepository pedidoLoteRepository;

    public Pedido toEntity(PedidoRequestDto pedidoRequestDTO) {
        Pedido pedido = new Pedido();
        pedido.setDtPedido(pedidoRequestDTO.getDtPedido());
        pedido.setPreco(pedidoRequestDTO.getPreco());
        pedido.setDescricao(pedidoRequestDTO.getDescricao());
        pedido.setTipoEntrega(pedidoRequestDTO.getTipoEntrega());
        pedido.setStatus(pedidoRequestDTO.getStatus());

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o id: " + pedidoRequestDTO.getClienteId()));

        pedido.setCliente(cliente);

        return pedido;
    }

    public PedidoResponseDto toResponseDTO(Pedido pedido) {
        PedidoResponseDto responseDTO = new PedidoResponseDto();
        responseDTO.setId(pedido.getId());
        responseDTO.setDtPedido(pedido.getDtPedido());
        responseDTO.setPreco(pedido.getPreco());
        responseDTO.setDescricao(pedido.getDescricao());
        responseDTO.setTipoEntrega(pedido.getTipoEntrega());
        responseDTO.setStatus(pedido.getStatus());
        responseDTO.setClienteId(pedido.getCliente().getId());
        responseDTO.setClienteNome(pedido.getCliente().getNome());

        Optional<PedidoVela> pedidoLote = pedidoLoteRepository.findByPedidoId(pedido.getId());  // Correção aqui

        pedidoLote.ifPresent(lote -> {
            PedidoVelaResponseDto pedidoLoteDto = new PedidoVelaResponseDto();
            pedidoLoteDto.setId(lote.getId());
            pedidoLoteDto.setFkPedido(lote.getPedido().getId());  // Correção dos campos
            pedidoLoteDto.setFkVela(lote.getVela().getId());  // Correção dos campos
            pedidoLoteDto.setQtdVelas(lote.getQuantidade());  // Adicionando a quantidade
            responseDTO.setPedidoLoteResponseDto(pedidoLoteDto);
        });

        return responseDTO;
    }

}
