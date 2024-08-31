package com.velas.vivene.inventory.manager.dto.venda;

import com.velas.vivene.inventory.manager.commons.Pagamento;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.Venda;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VendaMapper {

    private final PedidoRepository pedidoRepository;

    public Venda toEntity(VendaRequestDto vendaRequestDTO) {
        Venda venda = new Venda();

        Pedido pedido = pedidoRepository.findById(vendaRequestDTO.getPedidoId())
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado com o id: " + vendaRequestDTO.getPedidoId()));

        if (!"Finalizado".equalsIgnoreCase(pedido.getStatus())) {
            throw new IllegalArgumentException("O pedido deve estar finalizado para ser vendido.");
        }

        venda.setPedido(pedido);
        venda.setMetodoPag(vendaRequestDTO.getMetodoPag());

        return venda;
    }

    public VendaResponseDto toResponseDTO(Venda venda) {
        VendaResponseDto responseDTO = new VendaResponseDto();
        responseDTO.setId(venda.getId());
        responseDTO.setPedidoId(venda.getPedido().getId());
        responseDTO.setMetodoPag(venda.getMetodoPag());
        responseDTO.setPrecoTotal(venda.getPedido().getPreco());
        responseDTO.setStatusPedido(venda.getPedido().getStatus());

        return responseDTO;
    }
}