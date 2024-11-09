package com.velas.vivene.inventory.manager.dto.top5velasmaisvendidas;

import com.velas.vivene.inventory.manager.entity.view.TopCincoVelas;
import org.springframework.stereotype.Component;

@Component
public class Top5VelasMaisVendidasMapper {

    public Top5VelasMaisVendidasResponse toDto(TopCincoVelas top5VelasMaisVendidas) {
        if (top5VelasMaisVendidas == null) {
            return null;
        }

        Top5VelasMaisVendidasResponse top5VelasMaisVendidasResponse = new Top5VelasMaisVendidasResponse();
        top5VelasMaisVendidasResponse.setNomeVela(top5VelasMaisVendidas.getNomeVela());
        top5VelasMaisVendidasResponse.setTotalVendido(top5VelasMaisVendidas.getTotalVendido());

        return top5VelasMaisVendidasResponse;
    }
}
