package com.projectapp.service.dto;

import com.projectapp.enumerated.StatusVeiculo;
import lombok.Data;

import java.util.List;

@Data
public class VeiculoDTO {
    private String placa;
    private String marca;
    private Long quilometragem;
    private StatusVeiculo status;
    private List<VeiculoPneuDTO> pneus;
}

