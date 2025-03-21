package com.projectapp.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoPneuDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String veiculoPlaca;
    private PneuDTO pneu;
    private String posicao;


}