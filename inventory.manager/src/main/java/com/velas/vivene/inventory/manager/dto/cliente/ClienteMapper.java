package com.velas.vivene.inventory.manager.dto.cliente;

import com.velas.vivene.inventory.manager.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    public Cliente toEntity(ClienteRequestDto clienteRequestDto) {
        if (clienteRequestDto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDto.getNome());
        cliente.setTelefone(clienteRequestDto.getTelefone());
        cliente.setQtdPedidos(clienteRequestDto.getQtdPedidos());

        return cliente;
    }

    public ClienteResponseDto toResponseDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteResponseDto clienteResponseDto = new ClienteResponseDto();
        clienteResponseDto.setId(cliente.getId());
        clienteResponseDto.setNome(cliente.getNome());
        clienteResponseDto.setTelefone(cliente.getTelefone());
        clienteResponseDto.setQtdPedidos(cliente.getQtdPedidos());

        return clienteResponseDto;
    }

}
