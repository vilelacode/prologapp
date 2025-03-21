package com.projectapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectapp.config.CustomExceptions.PneuException;
import com.projectapp.repository.VeiculoPneuRepository;
import com.projectapp.service.dto.PneuDTO;
import com.projectapp.enumerated.StatusPneu;
import com.projectapp.repository.PneuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.projectapp.util.TestConstants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PneuControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PneuRepository pneuRepository;
    @Autowired private VeiculoPneuRepository veiculoPneuRepository;

    private PneuDTO pneuDTO;

    private static final String BUSCA_NUMERO_FOGO_URL = "/api/v1/pneus/{numeroFogo}";
    private static final String PNEUS_URL = "/api/v1/pneus";

    @BeforeEach
    void setUp() {
        veiculoPneuRepository.deleteAll();
        pneuRepository.deleteAll();

        pneuDTO = new PneuDTO();
        pneuDTO.setNumeroFogo(PLACA_1);
        pneuDTO.setMarca(MARCA_VEICULO_1);
        pneuDTO.setPressaoAtual(32.5);
        pneuDTO.setStatus(StatusPneu.DISPONIVEL);
    }

    @Test
    void salvarDeveCriarNovoPneuComSucesso() throws Exception {
        mockMvc.perform(post(PNEUS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pneuDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroFogo", is(pneuDTO.getNumeroFogo())))
                .andExpect(jsonPath("$.marca", is(pneuDTO.getMarca())))
                .andExpect(jsonPath("$.pressaoAtual", is(pneuDTO.getPressaoAtual())))
                .andExpect(jsonPath("$.status", is(pneuDTO.getStatus().name())));
    }

    @Test
    void listarTodosDeveRetornarListaComPeloMenosUmPneu() throws Exception {
        pneuRepository.save(objectMapper.convertValue(pneuDTO, com.projectapp.entity.Pneu.class));

        mockMvc.perform(get(PNEUS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].numeroFogo", is(pneuDTO.getNumeroFogo())));
    }

    @Test
    void buscarPorIdDeveRetornarPneuQuandoExistir() throws Exception {
        pneuRepository.save(objectMapper.convertValue(pneuDTO, com.projectapp.entity.Pneu.class));

        mockMvc.perform(get(BUSCA_NUMERO_FOGO_URL, pneuDTO.getNumeroFogo()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.numeroFogo", is(pneuDTO.getNumeroFogo())))
                .andExpect(jsonPath("$.marca", is(pneuDTO.getMarca())));
    }

    @Test
    void buscarPorNumeroFogoQuandoNaoExistir() throws Exception {
        mockMvc.perform(get(BUSCA_NUMERO_FOGO_URL, PLACA_2))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PneuException));
    }
}

