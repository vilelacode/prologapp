package com.projectapp.util.mappers;

import com.projectapp.entity.Veiculo;
import com.projectapp.service.dto.VeiculoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

@Mapper(uses = VeiculoPneuMapper.class)
public interface VeiculoMapper {

    VeiculoMapper INSTANCE = Mappers.getMapper(VeiculoMapper.class);

    @Mapping(target = "pneus", source = "pneus")
    VeiculoDTO toDto(Veiculo veiculo);

    @Mapping(target = "pneus", ignore = true)
    Veiculo toEntity(VeiculoDTO dto);

}