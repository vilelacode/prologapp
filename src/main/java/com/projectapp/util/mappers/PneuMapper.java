package com.projectapp.util.mappers;

import com.projectapp.entity.Pneu;
import com.projectapp.service.dto.PneuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PneuMapper {

    PneuMapper INSTANCE = Mappers.getMapper(PneuMapper.class);

    PneuDTO toDto(Pneu pneu);
    Pneu toEntity(PneuDTO pneuDTO);
}