package com.projectapp.controller;

import com.projectapp.enumerated.StatusPneu;
import com.projectapp.service.dto.PneuDTO;
import com.projectapp.service.dto.VeiculoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectapp.service.dto.VeiculoPneuDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static com.projectapp.enumerated.StatusVeiculo.ATIVO;
import static com.projectapp.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VeiculoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PNEUS_URL = "/api/v1/pneus";
    private static final String VEICULOS_URL = "/api/v1/veiculos";

    @Test
    void listarTodosDeveRetornarListaDePneus() throws Exception {
        mockMvc.perform(get(PNEUS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void criarVeiculoDeveRetornarVeiculoCriado() throws Exception {
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setPlaca(PLACA_1);
        veiculoDTO.setMarca(MARCA_VEICULO_1);
        veiculoDTO.setQuilometragem(50000L);
        veiculoDTO.setStatus(ATIVO);
        veiculoDTO.setPneus(
                new ArrayList<>() {{
                    add(new VeiculoPneuDTO(PLACA_1, new PneuDTO(NUMERO_FOGO_1, MARCA_PNEU_1, 30.0, StatusPneu.EM_USO),
                            "A"));
                }}
        );

        mockMvc.perform(post(VEICULOS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculoDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.placa").value(PLACA_1))
            .andExpect(jsonPath("$.marca").value(MARCA_VEICULO_1))
            .andExpect(jsonPath("$.quilometragem").value(50000))
            .andExpect(jsonPath("$.status").value(ATIVO.toString()));
    }

    @Test
    void devolverExcessaoAoCriarVeiculoComPlacaDuplicada() throws Exception {

        VeiculoDTO veiculoDTO1 = new VeiculoDTO();
        veiculoDTO1.setPlaca(PLACA_1);
        veiculoDTO1.setMarca(MARCA_VEICULO_1);
        veiculoDTO1.setQuilometragem(50000L);
        veiculoDTO1.setStatus(ATIVO);

        mockMvc.perform(post(VEICULOS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculoDTO1)))
            .andExpect(status().isBadRequest());


        VeiculoDTO veiculoDTO2 = new VeiculoDTO();
        veiculoDTO2.setPlaca(PLACA_1);
        veiculoDTO2.setMarca(MARCA_VEICULO_2);
        veiculoDTO2.setQuilometragem(30000L);
        veiculoDTO2.setStatus(ATIVO);

        mockMvc.perform(post(VEICULOS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculoDTO2)))
            .andExpect(status().isBadRequest());
    }
} 