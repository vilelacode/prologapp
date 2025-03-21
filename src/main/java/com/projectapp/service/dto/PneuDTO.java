package com.projectapp.service.dto;

import com.projectapp.enumerated.StatusPneu;
import lombok.Data;

@Data
public class PneuDTO {
    private String numeroFogo;
    private String marca;
    private Double pressaoAtual;
    private StatusPneu status;

    public PneuDTO(String numeroFogo, String marca, double pressaoAtual, StatusPneu status) {
        this.numeroFogo = numeroFogo;
        this.marca = marca;
        this.pressaoAtual = pressaoAtual;
        this.status = status;
    }

    public PneuDTO() {

    }
}