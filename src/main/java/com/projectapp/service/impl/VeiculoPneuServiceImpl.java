package com.projectapp.service.impl;

import com.projectapp.config.CustomExceptions.*;
import com.projectapp.entity.Pneu;
import com.projectapp.entity.Veiculo;
import com.projectapp.entity.VeiculoPneu;
import com.projectapp.entity.VeiculoPneuId;
import com.projectapp.enumerated.StatusPneu;
import com.projectapp.repository.PneuRepository;
import com.projectapp.repository.VeiculoPneuRepository;
import com.projectapp.repository.VeiculoRepository;
import com.projectapp.service.VeiculoPneuService;
import com.projectapp.service.dto.VeiculoPneuDTO;
import com.projectapp.util.mappers.VeiculoPneuMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.projectapp.util.Constants.*;

@Service
@RequiredArgsConstructor
public class VeiculoPneuServiceImpl implements VeiculoPneuService {
    
    private final VeiculoPneuRepository veiculoPneuRepository;
    private final VeiculoRepository veiculoRepository;
    private final PneuRepository pneuRepository;

    Logger log = LoggerFactory.getLogger(VeiculoPneuServiceImpl.class);
    
    @Override
    public List<VeiculoPneuDTO> listarPorVeiculo(String placa) {

        List<VeiculoPneuDTO> veiculoPneuDTOS = new ArrayList<>();
        try {
            var result = veiculoPneuRepository.findById_Veiculo_Placa(placa);
            result.forEach(VeiculoPneuMapper.INSTANCE::toDto);
            log.info("Veiculo Pneu: {}", result);
            return veiculoPneuDTOS;
        } catch (Exception e) {
            log.atError().log(e.getMessage());
            throw new VeiculoException(e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public VeiculoPneuDTO vincularPneu(String placa, String numeroFogo, String posicao) {

        Veiculo veiculo = validaVeiculoExistente(placa);
        Pneu pneu = validaPneuExistente(numeroFogo);
        validaPosicaoNoVeiculo(placa, posicao);

        VeiculoPneuId id = new VeiculoPneuId(veiculo, pneu, posicao);
        VeiculoPneu veiculoPneu = new VeiculoPneu();
        veiculoPneu.setId(id);

        pneu.setStatus(StatusPneu.EM_USO);
        pneuRepository.save(pneu);

        veiculoPneu = veiculoPneuRepository.save(veiculoPneu);
        log.info(PNEU_VINCULADO_VEICULO_EM_POSICAO, numeroFogo, placa, posicao);

        return VeiculoPneuMapper.INSTANCE.toDto(veiculoPneu);
    }

    private void validaPosicaoNoVeiculo(String placa, String posicao) {
        if (veiculoPneuRepository.existsById_Veiculo_PlacaAndId_Posicao(placa, posicao)) {
           log.atError().log(POSICAO_JA_OCUPADA_PARA_PLACA, placa);
           throw new VeiculoPneuException(POSICAO_JA_OCUPADA);
        }
    }

    private Veiculo validaVeiculoExistente(String placa) {
        return veiculoRepository.findByPlaca(placa)
            .orElseThrow(() -> {
                log.atError().log(VEICULO_NAO_ENCONTRADO);
                return new VeiculoException(VEICULO_NAO_ENCONTRADO);
            });
    }

    private Pneu validaPneuExistente(String numeroFogo) {
        return pneuRepository.findById(numeroFogo)
            .orElseThrow(() -> {
                log.atError().log(PNEU_NAO_ENCONTRADO_EM_ESTOQUE);
                throw new EntityNotFoundException(PNEU_NAO_ENCONTRADO);
            });
    }

    @Override
    @Transactional
    public void desvincularPneu(String placa, String numeroFogo) {
        VeiculoPneu veiculoPneu = veiculoPneuRepository.findById_Veiculo_PlacaAndId_Pneu_NumeroFogo(placa, numeroFogo)
            .orElseThrow(() -> new VeiculoException(PNEU_NAO_ENCONTRADO_NO_VEICULO));
            
        Pneu pneu = veiculoPneu.getId().getPneu();
        pneu.setStatus(StatusPneu.DISPONIVEL);
        pneuRepository.save(pneu);
        
        veiculoPneuRepository.delete(veiculoPneu);
        log.info(PNEU_DESVINCULADO_SUCESSO, numeroFogo);
    }
} 