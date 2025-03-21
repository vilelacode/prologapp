package com.projectapp.service;

import com.projectapp.service.dto.VeiculoDTO;
import com.projectapp.service.dto.VeiculoResumoDTO;

import java.util.List;

public interface VeiculoService {

    List<VeiculoResumoDTO> listarTodos();
    VeiculoDTO buscarPorId(String placa);
    VeiculoDTO salvar(VeiculoDTO dto);
}
