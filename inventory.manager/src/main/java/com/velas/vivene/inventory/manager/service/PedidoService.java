package com.velas.vivene.inventory.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.Pagamento;
import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.lote.LoteMapper;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoMapper;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoRequestDto;
import com.velas.vivene.inventory.manager.dto.pedido.PedidoResponseDto;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaRequestDto;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesMapper;
import com.velas.vivene.inventory.manager.dto.quantidadevendasseismeses.QuantidadeVendasSeisMesesResponse;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.view.QuantidadeVendasSeisMeses;
import com.velas.vivene.inventory.manager.repository.LoteRepository;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import com.velas.vivene.inventory.manager.repository.QuantidadeVendasSeisMesesRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

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

    public PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequest) {

        if (pedidoRequest == null) {
            throw new ValidationException("Os dados do pedido são obrigatórios.");
        }

        try {
            PedidoVelaRequestDto pedidoLote = new PedidoVelaRequestDto();

            Pedido pedido = pedidoMapper.toEntity(pedidoRequest);
            Pedido pedidoSave = pedidoRepository.save(pedido);

            pedidoLote.setPedidoId(pedidoSave.getId());
            pedidoLote.setVelaId(pedidoRequest.getLoteId());
            pedidoLote.setQuantidade(pedidoRequest.getQtdVelas());

            pedidoLoteService.createPedidoVela(pedidoLote);

            return pedidoMapper.toResponseDTO(pedidoSave);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar o pedido.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar pedido.");
        }
    }

    public PedidoResponseDto updatePedido(Integer id, PedidoRequestDto pedidoRequestDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));

        if (pedidoRequestDTO == null) {
            throw new ValidationException("Os dados do pedido são obrigatórios.");
        }

        try {
            pedido.setDtPedido(pedidoRequestDTO.getDtPedido());
            pedido.setDescricao(pedidoRequestDTO.getDescricao());
            pedido.setTipoEntrega(pedidoRequestDTO.getTipoEntrega());
            pedido.setStatus(pedidoRequestDTO.getStatus());

            Pedido updatedPedido = pedidoRepository.save(pedido);
            return pedidoMapper.toResponseDTO(updatedPedido);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar o pedido.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar pedido.");
        }
    }

    public PedidoResponseDto finalizaPedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));

        try {
            pedido.setStatus(Pagamento.FINALIADO.getDescricao());
            Pedido updatedPedido = pedidoRepository.save(pedido);
            return pedidoMapper.toResponseDTO(updatedPedido);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao finalizar o pedido.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao finalizar pedido.");
        }
    }

    public void deletePedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        pedidoRepository.delete(pedido);
    }

    public List<PedidoResponseDto> getAllPedidos() {
       List<PedidoResponseDto> pedidos = pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .collect(Collectors.toList());

        if (pedidos.isEmpty()) {
            throw new NoContentException("Não existe nenhum pedido no banco de dados");
        }

        return pedidos;
    }

    public PedidoResponseDto getPedidoById(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        return pedidoMapper.toResponseDTO(pedido);
    }

    public List<QuantidadeVendasSeisMesesResponse> getQuantidadeVendasSeisMeses() {
        List<QuantidadeVendasSeisMeses> vendas = quantidadeVendasSeisMesesRepository.findAll();
        if (vendas.isEmpty()) {
            throw new NoContentException("Não existe nenhuma venda no banco de dados");
        }
        List<QuantidadeVendasSeisMesesResponse> vendasResponse = new ArrayList<>();

        for (QuantidadeVendasSeisMeses v : vendas) {
            QuantidadeVendasSeisMesesResponse vendaR = new QuantidadeVendasSeisMesesResponse();
            vendaR = quantidadeVendasSeisMesesMapper.toDto(v);
            vendasResponse.add(vendaR);
        }

        return vendasResponse;
    }

}
