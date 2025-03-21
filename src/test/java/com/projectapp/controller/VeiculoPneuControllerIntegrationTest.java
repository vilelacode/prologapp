package com.projectapp.controller;

import com.projectapp.config.CustomExceptions;
import com.projectapp.entity.Pneu;
import com.projectapp.entity.Veiculo;
import com.projectapp.entity.VeiculoPneu;
import com.projectapp.entity.VeiculoPneuId;
import com.projectapp.enumerated.StatusPneu;
import com.projectapp.enumerated.StatusVeiculo;
import com.projectapp.repository.PneuRepository;
import com.projectapp.repository.VeiculoPneuRepository;
import com.projectapp.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.projectapp.util.Constants.VEICULO_NAO_ENCONTRADO;
import static com.projectapp.util.TestConstants.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VeiculoPneuControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private PneuRepository pneuRepository;

    @Autowired
    private VeiculoPneuRepository veiculoPneuRepository;

    private static final String VINCULAR_URL = "/api/v1/veiculos/{placa}/pneus/{numeroFogo}/posicao/{posicao}";
    private static final String DESVINCULAR_URL = "/api/v1/veiculos/{placa}/pneus/{numeroFogo}";

    private String placa;
    private String numeroFogo;
    private String posicao;


    @BeforeEach
    void setUp() {

        veiculoPneuRepository.deleteAll();
        pneuRepository.deleteAll();
        veiculoRepository.deleteAll();

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(PLACA_1);
        veiculo.setMarca(MARCA_VEICULO_1);
        veiculo.setQuilometragem(120000L);
        veiculo.setStatus(StatusVeiculo.ATIVO);
        veiculoRepository.save(veiculo);
        placa = veiculo.getPlaca();

        Pneu pneu = new Pneu();
        pneu.setNumeroFogo(NUMERO_FOGO_1);
        pneu.setMarca(MARCA_PNEU_1);
        pneu.setPressaoAtual(32.0);
        pneu.setStatus(StatusPneu.DISPONIVEL);
        pneuRepository.save(pneu);
        numeroFogo = pneu.getNumeroFogo();

        posicao = "A";
    }


    @Test
    void vincularPneuQuandoDadosValidosDeveVincularComSucesso() throws Exception {

        mockMvc.perform(post(VINCULAR_URL,
                        placa, numeroFogo, posicao))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pneu.numeroFogo", is(numeroFogo)))
                .andExpect(jsonPath("$.posicao", is(posicao)))
                .andExpect(jsonPath("$.pneu.status", is(EM_USO)));

        List<VeiculoPneu> veiculoPneus = veiculoPneuRepository.findById_Veiculo_Placa(placa);
        assertEquals(1, veiculoPneus.size());
        assertEquals(placa, veiculoPneus.get(0).getId().getVeiculo().getPlaca());
        assertEquals(numeroFogo, veiculoPneus.get(0).getId().getPneu().getNumeroFogo());
        assertEquals(posicao, veiculoPneus.get(0).getId().getPosicao());
    }

    @Test
    void vincularPneuQuandoVeiculoNaoExisteDeveRetornarExcessao() throws Exception {
        String placaInexistente = PLACA_2;

        mockMvc.perform(post(VINCULAR_URL, placaInexistente, numeroFogo, posicao))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("Erro no veículo")))
                .andExpect(jsonPath("$.message", is(VEICULO_NAO_ENCONTRADO)))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomExceptions.VeiculoException));
    }

    @Test
    void vincularPneuQuandoPneuNaoExisteDeveRetornarExcessao() throws Exception {
        String pneuInexistente = "999999";
        mockMvc.perform(post(VINCULAR_URL, placa, pneuInexistente,posicao))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void vincularPneuQuandoPosicaoJaExisteDeveRetornarExcessao400() throws Exception {
        VeiculoPneuId id = new VeiculoPneuId(
                veiculoRepository.findByPlaca(placa).orElseThrow(),
                pneuRepository.findByNumeroFogo(numeroFogo),
                posicao
        );
        VeiculoPneu vp = new VeiculoPneu();
        vp.setId(id);
        veiculoPneuRepository.save(vp);

        Pneu outroPneu = new Pneu();
        outroPneu.setNumeroFogo("654321");
        outroPneu.setMarca("Goodyear");
        outroPneu.setPressaoAtual(30.0);
        outroPneu.setStatus(StatusPneu.DISPONIVEL);
        pneuRepository.save(outroPneu);

        mockMvc.perform(post(VINCULAR_URL,
                placa, outroPneu.getNumeroFogo(), posicao))
                .andExpect(status().isBadRequest());
    }

    @Test
    void desvincularPneuQuandoPneuExisteDeveDesvincularComSucesso() throws Exception {
        VeiculoPneuId id = new VeiculoPneuId(
                veiculoRepository.findByPlaca(placa).orElseThrow(),
                pneuRepository.findByNumeroFogo(numeroFogo),
                posicao
        );
        VeiculoPneu vp = new VeiculoPneu();
        vp.setId(id);
        veiculoPneuRepository.save(vp);

        mockMvc.perform(delete(DESVINCULAR_URL, placa, numeroFogo))
                .andExpect(status().isNoContent());

        List<VeiculoPneu> veiculoPneus = veiculoPneuRepository.findById_Veiculo_Placa(placa);
        assertTrue(veiculoPneus.isEmpty());

        Pneu pneuAtualizado = pneuRepository.findById(numeroFogo).orElseThrow();
        assertEquals(StatusPneu.DISPONIVEL, pneuAtualizado.getStatus());
    }

    @Test
    void desvincularPneuQuandoPneuNaoExisteDeveRetornarExcessao404() throws Exception {
        String pneuInexistente = NUMERO_FOGO_2;
        mockMvc.perform(delete("/api/veiculos/{placa}/pneus/{numeroFogo}", placa, pneuInexistente))
                .andExpect(status().isNotFound());
    }
}
