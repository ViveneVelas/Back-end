package com.velas.vivene.inventory.manager.dto.vendasdasemana;

import com.velas.vivene.inventory.manager.entity.VendasDaSemana;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendasDaSemanaMapper {
    public VendasDaSemanaResponseDto toResponseDTO(VendasDaSemana venda) {
        if (venda == null) {
            return null;
        }

        VendasDaSemanaResponseDto vendasDto = new VendasDaSemanaResponseDto();
        vendasDto.setVendaId(venda.getVendaId());
        vendasDto.setMetodoPag(venda.getMetodoPag());
        vendasDto.setTelefone(venda.getTelefone());
        vendasDto.setNomeCliente(venda.getNomeCliente());
        vendasDto.setDataDoPedido(venda.getDataDoPedido());

        return vendasDto;
    }

    public List<VendasDaSemanaResponseDto> toResponseDTO(List<VendasDaSemana> vendas) {
        if (vendas == null) {
            return null;
        }

        List<VendasDaSemanaResponseDto> lista = new ArrayList<>();

        for (VendasDaSemana v: vendas) {
            VendasDaSemanaResponseDto vDto = toResponseDTO(v);
            lista.add(vDto);
        }


        return lista;
    }
}
