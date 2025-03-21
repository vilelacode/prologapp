package com.projectapp.service;

import com.projectapp.service.dto.VeiculoPneuDTO;
import java.util.List;

public interface VeiculoPneuService {
    
    List<VeiculoPneuDTO> listarPorVeiculo(String placa);
    
    VeiculoPneuDTO vincularPneu(String placa, String numeroFogo, String posicao);
    
    void desvincularPneu(String placa, String numeroFogo);
} 