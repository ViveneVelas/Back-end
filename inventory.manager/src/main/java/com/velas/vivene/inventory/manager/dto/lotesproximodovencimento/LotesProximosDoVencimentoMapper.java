package com.velas.vivene.inventory.manager.dto.lotesproximodovencimento;

import com.velas.vivene.inventory.manager.entity.view.LotesProximoDoVencimento;
import org.springframework.stereotype.Component;

@Component
public class LotesProximosDoVencimentoMapper {

    public LotesProximoDoVencimentoResponse toResponseDto(LotesProximoDoVencimento lote) {
        if (lote == null) {
            return null;
        }

        LotesProximoDoVencimentoResponse response = new LotesProximoDoVencimentoResponse();
        response.setNomeVela(lote.getNomeVela());
        response.setDataFabricacao(lote.getDataFabricacao());
        response.setDataValidade(lote.getDataValidade());
        response.setQtdDisponivel(lote.getQtdDisponivel());
        response.setLocalizacao(lote.getLocalizacao());

        return response;
    }
}
