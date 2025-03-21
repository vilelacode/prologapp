package com.projectapp.util.mappers;

import com.projectapp.entity.Pneu;
import com.projectapp.entity.Veiculo;
import com.projectapp.entity.VeiculoPneu;
import com.projectapp.entity.VeiculoPneuId;
import com.projectapp.service.dto.VeiculoPneuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = PneuMapper.class)
public interface VeiculoPneuMapper {

    VeiculoPneuMapper INSTANCE = Mappers.getMapper(VeiculoPneuMapper.class);

    @Mapping(target = "veiculoPlaca", source = "id.veiculo.placa")
    @Mapping(target = "pneu", source = "id.pneu")
    @Mapping(target = "posicao", source = "id.posicao")
    VeiculoPneuDTO toDto(VeiculoPneu veiculoPneu);

    @Mapping(target = "id", expression = "java(criarId(dto, veiculo, pneu))")
    VeiculoPneu toEntity(VeiculoPneuDTO dto, Veiculo veiculo, Pneu pneu);

    default VeiculoPneuId criarId(VeiculoPneuDTO dto, Veiculo veiculo, Pneu pneu) {
        if (dto == null || dto.getPneu() == null || veiculo == null || pneu == null || dto.getPosicao() == null) {
            throw new IllegalArgumentException("Nenhum campo do ID pode ser null");
        }

        return new VeiculoPneuId(veiculo, pneu, dto.getPosicao());
    }
}
