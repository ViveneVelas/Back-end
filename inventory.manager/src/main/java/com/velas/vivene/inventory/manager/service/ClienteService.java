package com.velas.vivene.inventory.manager.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.velas.vivene.inventory.manager.commons.*;
import com.velas.vivene.inventory.manager.commons.exceptions.*;
import com.velas.vivene.inventory.manager.dto.cliente.*;
import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.*;
import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.view.ClientesMaisCompras;
import com.velas.vivene.inventory.manager.repository.*;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final LerArquivos lerArquivos;
    private final ClienteMapper clienteMapper;
    private final ClientesMaisComprasRepository clientesMaisComprasRepository;
    private final ClientesMaisComprasMapper clientesMaisComprasMapper;

    public ClienteResponseDto createCliente(ClienteRequestDto clienteRequestDTO) {
        if (clienteRequestDTO == null) {
            throw new ValidationException("Os dados do cliente são obrigatórios.");
        }
        
        try {
            Cliente cliente = clienteMapper.toEntity(clienteRequestDTO);
            cliente = clienteRepository.save(cliente);
            return clienteMapper.toResponseDTO(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao salvar o cliente.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao criar cliente " + ex);
        }
    }

    public ClienteResponseDto updateCliente(Integer id, ClienteRequestDto clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));

        if (clienteRequestDTO == null) {
            throw new ValidationException("Os dados do cliente são obrigatórios.");
        }

        try {
            cliente.setNome(clienteRequestDTO.getNome());
            cliente.setTelefone(clienteRequestDTO.getTelefone());
            cliente.setQtdPedidos(clienteRequestDTO.getQtdPedidos());
            cliente = clienteRepository.save(cliente);
            return clienteMapper.toResponseDTO(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Violação de integridade de dados ao atualizar o cliente.");
        } catch (Exception ex) {
            throw new UnexpectedServerErrorException("Erro inesperado ao atualizar cliente " + ex);
        }
    }

    public void deleteCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
        clienteRepository.delete(cliente);
    }

    public List<ClienteResponseDto> getAllClientes() {
        List<ClienteResponseDto> clientes = clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            throw new NoContentException("Não existe nenhum cliente no banco de dados");
        }

        return clientes;
    }

    public ClienteResponseDto getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
        return clienteMapper.toResponseDTO(cliente);
    }

    public List<ClienteResponseDto> nomeCliente(String nome) {
        List<ClienteResponseDto> clientes = clienteRepository.findByNomeIgnoreCase(nome)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            throw new NoContentException("Não existe nenhum cliente no banco de dados");
        }

        return clientes;
    }

    public List<String> nomesClientes() {
        List<String> clientes = clienteRepository.findAllNames();

        if (clientes.isEmpty()) {
            throw new NoContentException("Não existe nenhum cliente no banco de dados");
        }

        return clientes;
    }

    public List<ClienteMaisComprasResponse> getClienteMaisCompras() {
        List<ClientesMaisCompras> clientes = clientesMaisComprasRepository.findAll();
        if (clientes.isEmpty()) {
            throw new NoContentException("Nenhum cliente com mais compras encontrado.");
        }

        List<ClienteMaisComprasResponse> clientesResponse = new ArrayList<>();
        for (ClientesMaisCompras c : clientes) {
            ClienteMaisComprasResponse clienteR = clientesMaisComprasMapper.toDto(c);
            clientesResponse.add(clienteR);
        }

        return clientesResponse;
    }

    public byte[] criarArqTxt() throws IOException {
        List<ClientesMaisCompras> clientes = clientesMaisComprasRepository.findAll();
        return GerarArquivosTxt.gerarArquivoTxt(clientes);
    }

    public void lerArqTxt(byte[] file) throws IOException {
        lerArquivos.importarArquivoTxt(file);
    }
}
