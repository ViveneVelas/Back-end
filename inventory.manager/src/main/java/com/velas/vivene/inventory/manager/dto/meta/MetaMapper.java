package com.velas.vivene.inventory.manager.dto.meta;

import com.velas.vivene.inventory.manager.entity.Meta;
import org.springframework.stereotype.Component;

@Component
public class MetaMapper {
    public Meta toEntity(MetaRequestDto metasRequestDto) {
        if (metasRequestDto == null) {
            return null;
        }

        Meta metas = new Meta();
        metas.setDataInicio(metasRequestDto.getDataInicio());
        metas.setDataFinal(metasRequestDto.getDataFinal());
        metas.setQtdVendas(metasRequestDto.getQtdVendas());

        return metas;
    }

    public MetaResponseDto toDto(Meta meta) {
        if (meta == null) {
            return null;
        }

        MetaResponseDto metaResponseDto = new MetaResponseDto();
        metaResponseDto.setId(meta.getId());
        metaResponseDto.setDataInicio(meta.getDataInicio());
        metaResponseDto.setDataFinal(meta.getDataFinal());
        metaResponseDto.setQtdVendas(meta.getQtdVendas());

        return metaResponseDto;
    }


}
