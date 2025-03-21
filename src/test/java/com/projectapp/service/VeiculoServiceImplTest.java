package com.projectapp.service;

import com.projectapp.config.CustomExceptions;
import com.projectapp.entity.Pneu;
import com.projectapp.enumerated.StatusPneu;
import com.projectapp.enumerated.StatusVeiculo;
import com.projectapp.repository.PneuRepository;
import com.projectapp.repository.VeiculoPneuRepository;
import com.projectapp.service.dto.PneuDTO;
import com.projectapp.service.dto.VeiculoDTO;
import com.projectapp.entity.Veiculo;
import com.projectapp.repository.VeiculoRepository;
import com.projectapp.service.dto.VeiculoPneuDTO;
import com.projectapp.service.dto.VeiculoResumoDTO;
import com.projectapp.service.impl.VeiculoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.projectapp.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceImplTest {

    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private VeiculoPneuRepository veiculoPneuRepository;
    @Mock
    private PneuRepository pneuRepository;

    @InjectMocks
    private VeiculoServiceImpl veiculoServiceImpl;

    private Veiculo veiculo;
    private VeiculoDTO veiculoDTO;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo();

        veiculo.setPlaca(PLACA_1);
        veiculo.setMarca(MARCA_VEICULO_1);
        veiculo.setQuilometragem(50000L);
        veiculo.setStatus(StatusVeiculo.ATIVO);

        veiculoDTO = new VeiculoDTO();

        veiculoDTO.setPlaca(PLACA_1);
        veiculoDTO.setMarca(MARCA_VEICULO_1);
        veiculoDTO.setQuilometragem(50000L);
        veiculoDTO.setStatus(StatusVeiculo.ATIVO);
        veiculoDTO.setPneus( new ArrayList<>(){{
            add(
                    new VeiculoPneuDTO(PLACA_1,
                    new PneuDTO(NUMERO_FOGO_1, MARCA_PNEU_1, 30.0, StatusPneu.DISPONIVEL),
                    "A"));
        }});

    }

    @Test
    void listarTodosDeveRetornarListaDeVeiculos() {
        when(veiculoRepository.findAll()).thenReturn(Arrays.asList(veiculo));

        List<VeiculoResumoDTO> resultado = veiculoServiceImpl.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(veiculoDTO.getPlaca(), resultado.get(0).placa());
        verify(veiculoRepository).findAll();
    }

    @Test
    void buscarPorPlacaQuandoVeiculoNaoExisteDeveRetornarExcecao() {
        when(veiculoRepository.findByPlaca(PLACA_2)).thenReturn(Optional.empty());
        assertThrows(CustomExceptions.VeiculoException.class, () -> veiculoServiceImpl.buscarPorId(PLACA_2));
        verify(veiculoRepository).findByPlaca(PLACA_2);
    }

    @Test
    void salvarDeveSalvarVeiculoERetornarDTO() {
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);
        when(pneuRepository.findByNumeroFogo(anyString())).thenReturn(null);
        when(pneuRepository.save(any(Pneu.class))).thenReturn(new Pneu(NUMERO_FOGO_1, MARCA_PNEU_1, 30.0, StatusPneu.DISPONIVEL));

        VeiculoDTO resultado = veiculoServiceImpl.salvar(veiculoDTO);

        assertNotNull(resultado);
        assertEquals(veiculoDTO.getPlaca(), resultado.getPlaca());
        assertEquals(veiculoDTO.getPlaca(), resultado.getPlaca());
        verify(veiculoRepository).save(any(Veiculo.class));
    }
} 