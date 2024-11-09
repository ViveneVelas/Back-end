package com.velas.vivene.inventory.manager.dto.velamaisvendida;

import com.velas.vivene.inventory.manager.entity.view.VelaMaisVendida;
import org.springframework.stereotype.Component;

@Component
public class VelaMaisVendidaMapper {

    public VelaMaisVendidaResponse toResponseDTO(VelaMaisVendida vela) {
        if (vela == null) {
            return null;
        }

        VelaMaisVendidaResponse velaMaisVendidaResponse = new VelaMaisVendidaResponse();
        velaMaisVendidaResponse.setNomeVela(vela.getNomeVela());
        velaMaisVendidaResponse.setQtd(vela.getQtd());

        return velaMaisVendidaResponse;
    }
}
