package com.velas.vivene.inventory.manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.velas.vivene.inventory.manager.commons.exceptions.CustomDataIntegrityViolationException;
import com.velas.vivene.inventory.manager.commons.exceptions.NoContentException;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.commons.exceptions.UnexpectedServerErrorException;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaMapper;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaRequestDto;
import com.velas.vivene.inventory.manager.dto.pedidovela.PedidoVelaResponseDto;
import com.velas.vivene.inventory.manager.entity.Pedido;
import com.velas.vivene.inventory.manager.entity.PedidoVela;
import com.velas.vivene.inventory.manager.entity.Vela;
import com.velas.vivene.inventory.manager.repository.PedidoRepository;
import com.velas.vivene.inventory.manager.repository.PedidoVelaRepository;
import com.velas.vivene.inventory.manager.repository.VelaRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoVelaService {

    private final PedidoVelaRepository pedidoVelaRepository;
    private final PedidoVelaMapper pedidoVelaMapper;
    private final PedidoRepository pedidoRepository;
    private final VelaRepository loteRepository;

    public PedidoVelaResponseDto createPedidoVela(PedidoVelaRequestDto pedidoVelaRequestDto) {
        if (pedidoVelaRequestDto == null) {
            throw new ValidationException("Os dados do pedido vela são obrigatórios.");
        }

        try {
            PedidoVela pedidoVela = pedidoVelaMapper.toEntity(pedidoVelaRequestDto);
            PedidoVela pedidoVelaSalvo = pedidoVelaRepository.save(pedidoVela);
            return pedidoVelaMapper.toResponseDto(pedidoVelaSalvo);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar o pedido vela.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar pedido vela " + ex);
        }
    }

    public PedidoVelaResponseDto updatePedidoVela(Integer id, PedidoVelaRequestDto pedidoVelaRequestDto) {
        PedidoVela pedidoVela = pedidoVelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Vela não encontrado com o id: " + id));
        Pedido pedido = pedidoRepository.findById(pedidoVelaRequestDto.getPedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o id: " + id));
        Vela lote = loteRepository.findById(pedidoVelaRequestDto.getVelaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vela não encontrado com o id: " + id));

        if (pedidoVelaRequestDto == null) {
            throw new ValidationException("Os dados do pedido vela são obrigatórios.");
        }

        try {
            pedidoVela.setPedido(pedido);
            pedidoVela.setVela(lote);
            pedidoVela.setQuantidade(pedidoVelaRequestDto.getQuantidade());

            PedidoVela pedidoVelaSalvo = pedidoVelaRepository.save(pedidoVela);
            return pedidoVelaMapper.toResponseDto(pedidoVelaSalvo);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar o pedido vela.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar pedido vela " + ex);
        }
    }

    public void deletePedidoVela(Integer id) {
        PedidoVela pedidoVela = pedidoVelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Vela não encontrado com o id: " + id));
        pedidoVelaRepository.delete(pedidoVela);
    }

    public List<PedidoVelaResponseDto> getAllPedidoVela() {
        List<PedidoVelaResponseDto> pedido_velas = pedidoVelaRepository.findAll()
                .stream()
                .map(pedidoVelaMapper::toResponseDto)
                .collect(Collectors.toList());

        if (pedido_velas.isEmpty()) {
            throw new NoContentException("Não existe nenhum pedido vela no banco de dados");
        }

        return pedido_velas;
    }

    public PedidoVelaResponseDto getPedidoVelaById(Integer id) {
        PedidoVela pedidoVela = pedidoVelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido Vela não encontrado com o id: " + id));
        return pedidoVelaMapper.toResponseDto(pedidoVela);

    }
}
