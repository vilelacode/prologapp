package com.projectapp.service.dto;

import com.projectapp.enumerated.StatusVeiculo;

public record VeiculoResumoDTO(String placa, String marca, Long quilometragem, StatusVeiculo status) {
    public VeiculoResumoDTO(VeiculoDTO veiculoDTO) {
        this(veiculoDTO.getPlaca(), veiculoDTO.getMarca(), veiculoDTO.getQuilometragem(), veiculoDTO.getStatus());
    }
}
