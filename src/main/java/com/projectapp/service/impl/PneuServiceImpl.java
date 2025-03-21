package com.projectapp.service.impl;

import com.projectapp.config.CustomExceptions.PneuException;
import com.projectapp.entity.Pneu;
import com.projectapp.repository.PneuRepository;
import com.projectapp.service.PneuService;
import com.projectapp.service.dto.PneuDTO;
import com.projectapp.util.mappers.PneuMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.projectapp.util.Constants.*;

@Service
@RequiredArgsConstructor
public class PneuServiceImpl implements PneuService {
    
    private final PneuRepository pneuRepository;

    Logger log = LoggerFactory.getLogger(PneuServiceImpl.class);

    @Override
    public List<PneuDTO> listarTodos() {
        return pneuRepository.findAll()
            .stream()
            .map(PneuMapper.INSTANCE::toDto)
            .sorted(Comparator.comparing(PneuDTO::getNumeroFogo))
            .toList();
    }
    
    @Override
    public PneuDTO buscarPorNumeroFogo(String numeroFogo) {
        Pneu pneu = pneuRepository.findById(numeroFogo)
            .orElseThrow(() -> new PneuException(PNEU_NAO_ENCONTRADO));
        return PneuMapper.INSTANCE.toDto(pneu);
    }
    
    @Override
    @Transactional
    public PneuDTO salvar(PneuDTO pneuDTO) {
        try {
            Optional.ofNullable(pneuRepository
                    .findByNumeroFogo(pneuDTO.getNumeroFogo()))
                    .ifPresent(pneu -> {
                        throw new PneuException(NUMERO_FOGO_CADASTRADO);
                    });

            var pneu = pneuRepository.save(PneuMapper.INSTANCE.toEntity(pneuDTO));
            log.info(SALVAR_PNEU_SUCESSO);
            return PneuMapper.INSTANCE.toDto(pneu);
        }
        catch (Exception e) {
            log.atError().log(e.getMessage());
            throw new PneuException(ERRO_SALVAR_PNEU);
        }
    }
} 