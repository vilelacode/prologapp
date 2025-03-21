package com.projectapp.service;

import com.projectapp.service.dto.PneuDTO;

import java.util.List;

public interface PneuService {

    List<PneuDTO> listarTodos();
    PneuDTO buscarPorNumeroFogo(String numeroFogo);
    PneuDTO salvar(PneuDTO pneuDTO);
}
