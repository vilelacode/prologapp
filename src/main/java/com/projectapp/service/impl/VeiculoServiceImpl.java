package com.projectapp.service.impl;

import com.projectapp.config.CustomExceptions.*;
import com.projectapp.entity.Pneu;
import com.projectapp.entity.VeiculoPneu;
import com.projectapp.enumerated.StatusPneu;
import com.projectapp.repository.PneuRepository;
import com.projectapp.repository.VeiculoPneuRepository;
import com.projectapp.service.dto.PneuDTO;
import com.projectapp.service.dto.VeiculoDTO;
import com.projectapp.entity.Veiculo;
import com.projectapp.repository.VeiculoRepository;
import com.projectapp.service.VeiculoService;
import com.projectapp.service.dto.VeiculoPneuDTO;
import com.projectapp.service.dto.VeiculoResumoDTO;
import com.projectapp.util.mappers.PneuMapper;
import com.projectapp.util.mappers.VeiculoMapper;
import com.projectapp.util.mappers.VeiculoPneuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.projectapp.util.Constants.*;

@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {
    
    private final VeiculoRepository veiculoRepository;
    private final PneuRepository pneuRepository;
    private final VeiculoPneuRepository veiculoPneuRepository;

    Logger log = Logger.getLogger(VeiculoServiceImpl.class.getName());

    @Override
    public List<VeiculoResumoDTO> listarTodos() {
        return veiculoRepository.findAll().stream()
                .map(veiculo -> new VeiculoResumoDTO(veiculo.getPlaca(), veiculo.getMarca(), veiculo.getQuilometragem(), veiculo.getStatus()))
                .toList();
    }
    
    @Override
    public VeiculoDTO buscarPorId(String placa) {
        var veiculo = veiculoRepository.findByPlaca(placa).orElseThrow(() -> new VeiculoException(VEICULO_NAO_ENCONTRADO));
        return VeiculoMapper.INSTANCE.toDto(veiculo);
    }
    
    @Override
    @Transactional
    public VeiculoDTO salvar(VeiculoDTO dto) {

        try {
            var veiculo = veiculoRepository.save(VeiculoMapper.INSTANCE.toEntity(dto));
            Veiculo finalVeiculo = veiculo;
            List<VeiculoPneu> novosPneus = dto.getPneus().stream()
                    .peek(this::validarPneuDTO)
                    .map(pneuDTO -> {
                        Pneu pneu = acharOuCriarPneu(pneuDTO.getPneu());
                        return VeiculoPneuMapper.INSTANCE.toEntity(pneuDTO, finalVeiculo, pneu);
                    })
                    .toList();

            veiculoPneuRepository.saveAll(novosPneus);

            return dto;
        } catch (Exception e) {
            log.severe(e.getMessage());
            throw new VeiculoException(ERRO_AO_SALVAR_VEICULO);
        }
    }

    private void validarPneuDTO(VeiculoPneuDTO pneuDTO) {
        if (pneuDTO.getPneu() == null || pneuDTO.getPosicao() == null) {
            throw new IllegalArgumentException(PNEU_E_POSICAO_OBRIGATORIOS);
        }
    }

    private Pneu acharOuCriarPneu(PneuDTO pneuDTO) {
        return Optional.ofNullable(pneuRepository.findByNumeroFogo(pneuDTO.getNumeroFogo()))
                .map(pneuExistente -> {
                    if (pneuExistente.getStatus() == StatusPneu.EM_USO) {
                        throw new PneuException(PNEU_EM_USO);
                    }
                    pneuExistente.setStatus(StatusPneu.EM_USO);
                    return pneuExistente;
                })
                .orElseGet(() -> {
                    Pneu novoPneu = PneuMapper.INSTANCE.toEntity(pneuDTO);
                    novoPneu.setStatus(StatusPneu.EM_USO);
                    return pneuRepository.save(novoPneu);
                });
    }
} 