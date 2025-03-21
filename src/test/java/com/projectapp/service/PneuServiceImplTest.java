package com.projectapp.service;

import com.projectapp.entity.Pneu;
import com.projectapp.enumerated.StatusPneu;
import com.projectapp.repository.PneuRepository;
import com.projectapp.service.dto.PneuDTO;
import com.projectapp.service.impl.PneuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PneuServiceImplTest {

    @Mock
    private PneuRepository pneuRepository;

    @InjectMocks
    private PneuServiceImpl pneuServiceImpl;

    private Pneu pneu;
    private PneuDTO pneuDTO;

    @BeforeEach
    void setUp() {
        pneu = new Pneu();
        pneu.setNumeroFogo("FOGO001");
        pneu.setMarca("Michelin");
        pneu.setPressaoAtual(32.0);
        pneu.setStatus(StatusPneu.DISPONIVEL);

        pneuDTO = new PneuDTO();
        pneuDTO.setNumeroFogo("FOGO001");
        pneuDTO.setMarca("Michelin");
        pneuDTO.setPressaoAtual(32.0);
        pneuDTO.setStatus(StatusPneu.DISPONIVEL);
    }

    @Test
    void listarTodosDeveRetornarListaDePneus() {
        when(pneuRepository.findAll()).thenReturn(Collections.singletonList(pneu));

        List<PneuDTO> resultado = pneuServiceImpl.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pneuDTO.getNumeroFogo(), resultado.get(0).getNumeroFogo());
        verify(pneuRepository).findAll();
    }

    @Test
    void buscarPorIdQuandoPneuExisteDeveRetornarPneuDTO() {
        when(pneuRepository.findById(anyString())).thenReturn(Optional.of(pneu));

        PneuDTO resultado = pneuServiceImpl.buscarPorNumeroFogo("FOGO001");

        assertNotNull(resultado);
        assertEquals(pneuDTO.getNumeroFogo(), resultado.getNumeroFogo());
        verify(pneuRepository).findById("FOGO001");
    }

    @Test
    void buscarPorIdQuandoPneuNaoExisteDeveLancarExcecao() {
        when(pneuRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pneuServiceImpl.buscarPorNumeroFogo("FOGO001"));
        verify(pneuRepository).findById("FOGO001");
    }

    @Test
    void salvar_DeveSalvarPneuERetornarDTO() {
        when(pneuRepository.save(any(Pneu.class))).thenReturn(pneu);

        PneuDTO resultado = pneuServiceImpl.salvar(pneuDTO);

        assertNotNull(resultado);
        assertEquals(pneuDTO.getNumeroFogo(), resultado.getNumeroFogo());
        verify(pneuRepository).save(any(Pneu.class));
    }
} 