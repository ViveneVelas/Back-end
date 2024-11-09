package com.velas.vivene.inventory.manager.dto.clientesmaiscompras;

import com.velas.vivene.inventory.manager.entity.view.ClientesMaisCompras;
import org.springframework.stereotype.Component;

@Component
public class ClientesMaisComprasMapper {

    public ClienteMaisComprasResponse toDto(ClientesMaisCompras cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteMaisComprasResponse response = new ClienteMaisComprasResponse();
        response.setNomeCliente(cliente.getNomeCliente());
        response.setNumPedidos(cliente.getNumPedidos());

        return response;
    }
}
