package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.Pagamento;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoMapper;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.dto.pedidolote.PedidoLoteRequestDto;
import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.Lote;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoLote;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
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
    private final ClienteRepository clienteRepository;
    private final LoteRepository loteRepository;

    public Pedido criarPedido(PedidoRequestDto pedidoRequest) {
        Cliente cliente = clienteRepository.findById(pedidoRequest.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setDtPedido(pedidoRequest.getDtPedido());
        pedido.setStatus(pedidoRequest.getStatus());
        pedido.setDescricao(pedidoRequest.getDescricao());
        pedido.setTipoEntrega(pedidoRequest.getTipoEntrega());
        pedido.setCliente(cliente);

        List<PedidoLote> pedidoLotes = new ArrayList<>();

        System.out.println(pedidoRequest.getPedidoLotes());
        for (PedidoLoteRequestDto pedidoLoteRequest : pedidoRequest.getPedidoLotes()) {
            Optional<Lote> lote = loteRepository.findById(pedidoLoteRequest.getLoteId());
            if(lote.get() == null){
                return null;
            }else {


                PedidoLote pedidoLote = new PedidoLote();
                pedidoLote.setLote(lote.get());
                pedidoLote.setPedido(pedido);
                pedido.getPedidoLotes().add(pedidoLote);

                pedidoLotes.add(pedidoLote);
            }
        }

        pedido.setPedidoLotes(pedidoLotes);

        return pedidoRepository.save(pedido);
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
}