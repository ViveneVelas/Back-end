package com.velas.vivene.inventory.manager.dto.pedido;
import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PedidoMapper {

    private final ClienteRepository clienteRepository;

    public Pedido toEntity(PedidoRequestDto pedidoRequestDTO) {
        Pedido pedido = new Pedido();
        pedido.setDtPedido(pedidoRequestDTO.getDtPedido());
        pedido.setPreco(pedidoRequestDTO.getPreco());
        pedido.setDescricao(pedidoRequestDTO.getDescricao());
        pedido.setTipoEntrega(pedidoRequestDTO.getTipoEntrega());

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

        return responseDTO;
    }
}