package com.velas.vivene.inventory.manager.service;

import com.velas.vivene.inventory.manager.commons.GerarArquivosTxt;
import com.velas.vivene.inventory.manager.commons.LerArquivos;
import com.velas.vivene.inventory.manager.commons.exceptions.ResourceNotFoundException;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteMapper;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteRequestDto;
import com.velas.vivene.inventory.manager.dto.cliente.ClienteResponseDto;
import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.ClienteMaisComprasResponse;
import com.velas.vivene.inventory.manager.dto.clientesmaiscompras.ClientesMaisComprasMapper;
import com.velas.vivene.inventory.manager.entity.Cliente;
import com.velas.vivene.inventory.manager.entity.view.ClientesMaisCompras;
import com.velas.vivene.inventory.manager.repository.ClienteRepository;
import com.velas.vivene.inventory.manager.repository.ClientesMaisComprasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClientesMaisComprasRepository clientesMaisComprasRepository;
    private final ClientesMaisComprasMapper clientesMaisComprasMapper;

    public ClienteResponseDto createCliente(ClienteRequestDto clienteRequestDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteRequestDTO);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    public ClienteResponseDto updateCliente(Integer id, ClienteRequestDto clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));

        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setTelefone(clienteRequestDTO.getTelefone());
        cliente.setQtdPedidos(clienteRequestDTO.getQtdPedidos());

        cliente = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    public void deleteCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
        clienteRepository.delete(cliente);
    }

    public List<ClienteResponseDto> getAllClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDto getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
        return clienteMapper.toResponseDTO(cliente);
    }

    public List<ClienteMaisComprasResponse> getClienteMaisCompras() {
        List<ClientesMaisCompras> clientes = clientesMaisComprasRepository.findAll();
        List<ClienteMaisComprasResponse> clientesResponse = new ArrayList<>();

        for (ClientesMaisCompras c : clientes) {
            ClienteMaisComprasResponse clienteR = new ClienteMaisComprasResponse();
            clienteR = clientesMaisComprasMapper.toDto(c);
            clientesResponse.add(clienteR);
        }

        return clientesResponse;
    }

    public byte[] criarArqTxt() throws IOException {
        List<ClientesMaisCompras> clientes = clientesMaisComprasRepository.findAll();
        return GerarArquivosTxt.gerarArquivoTxt(clientes);
    }

    public void lerArqTxt(byte[] file) throws IOException {
        LerArquivos.importarArquivoTxt(file);

    }
}