package com.velas.vivene.inventory.manager.dto.pedido;

import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoVelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public PedidoResponseDto toResponseDTO(Pedido pedido, List<VelaPedidoListaDto> lista) {
        PedidoResponseDto responseDTO = new PedidoResponseDto();

        responseDTO.setId(pedido.getId());
        responseDTO.setDtPedido(pedido.getDtPedido());
        responseDTO.setDescricao(pedido.getDescricao());
        responseDTO.setTipoEntrega(pedido.getTipoEntrega());
        responseDTO.setPreco(pedido.getPreco());
        responseDTO.setClienteId(pedido.getCliente().getId());
        responseDTO.setStatus(pedido.getStatus());
        responseDTO.setClienteNome(pedido.getCliente().getNome());
        responseDTO.setListaDeVelas(lista);

        return responseDTO;
    }

    public PedidoCalendarioResponseDto toResponseDTOC(Pedido pedido, List<VelaPedidoListaDto> lista) {
        PedidoCalendarioResponseDto responseDTO = new PedidoCalendarioResponseDto();

        responseDTO.setId(pedido.getId());
        responseDTO.setEnd(pedido.getDtPedido());
        responseDTO.setStart(pedido.getDtPedido());
        responseDTO.setDate(pedido.getDtPedido());
        responseDTO.setPreco(pedido.getPreco());
        responseDTO.setClienteId(pedido.getCliente().getId());
        responseDTO.setClienteNome(pedido.getCliente().getNome());
        responseDTO.setListaDeVelas(lista);
        responseDTO.setColor("#7CC6D7");
        responseDTO.setTitulo("Pedido - Entrega");
        responseDTO.setTitle("Pedido - Entrega");

        return responseDTO;
    }

}
