package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.Pagamento;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoMapper;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaRequestDto;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesMapper;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesResponse;
import com.velas.vivene.inventory.manager.entity.*;
import com.velas.vivene.inventory.manager.entity.view.QuantidadeVendasSeisMeses;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import com.velas.vivene.inventory.manager.repository.QuantidadeVendasSeisMesesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final LoteRepository loteRepository;
    private final PedidoVelaService pedidoLoteService;
    private final QuantidadeVendasSeisMesesRepository quantidadeVendasSeisMesesRepository;
    private final QuantidadeVendasSeisMesesMapper quantidadeVendasSeisMesesMapper;
    private final LoteMapper loteMapper;
    private final LoteService loteService;
    private final EntityManager entityManager;

    public PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequest) {
        PedidoVelaRequestDto pedidoLote = new PedidoVelaRequestDto();

        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);
        Pedido pedidoSave = pedidoRepository.save(pedido);

        pedidoLote.setPedidoId(pedidoSave.getId());
        pedidoLote.setVelaId(pedidoRequest.getLoteId());
        pedidoLote.setQuantidade(pedidoRequest.getQtdVelas());

        pedidoLoteService.createPedidoVela(pedidoLote);

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


    public List<PedidoResponseDto> getAllPedidosFiltro(LocalDate dtValidade, String nomeCliente, String nomeVela ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> query = cb.createQuery(Pedido.class);
        Root<Pedido> root = query.from(Pedido.class);

        List<Predicate> predicates = new ArrayList<>();

        if (dtValidade != null) {
            predicates.add(cb.equal(root.get("dtPedido"), dtValidade));
        }

        if (nomeCliente != null) {
            Join<Pedido, Cliente> clienteJoin = root.join("cliente");
            predicates.add(cb.like(clienteJoin.get("nome"), "%" + nomeCliente + "%"));
        }


        if (nomeVela != null) {
            Join<Pedido, PedidoVela> pedidoVelas = root.join("pedidoVelas");
                predicates.add(cb.like(pedidoVelas.get("vela").get("nome"), "%" + nomeVela + "%" ));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        List<Pedido> pedidos = entityManager.createQuery(query).getResultList();
        List<PedidoResponseDto> respostas = new ArrayList<>();

        for (Pedido p : pedidos){
            respostas.add(pedidoMapper.toResponseDTO(p));
        }

        return respostas;

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