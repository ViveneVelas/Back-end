package com.velas.vivene.inventory.manager.dto.ultimametaseismeses;

import com.velas.vivene.inventory.manager.entity.view.UltimaMetaSeisMeses;
import org.springframework.stereotype.Component;

@Component
public class UltimaMetaSeisMesesMapper {

    public UltimaMetaSeisMesesResponse toDto(UltimaMetaSeisMeses ultimaMetaSeisMeses) {
        if(ultimaMetaSeisMeses == null) {
            return null;
        }

        UltimaMetaSeisMesesResponse ultimaMetaSeisMesesResponse = new UltimaMetaSeisMesesResponse();
        ultimaMetaSeisMesesResponse.setDataInicio(ultimaMetaSeisMeses.getDataInicio());
        ultimaMetaSeisMesesResponse.setQtdVendas(ultimaMetaSeisMeses.getQtdVendas());

        return ultimaMetaSeisMesesResponse;
    }

}
