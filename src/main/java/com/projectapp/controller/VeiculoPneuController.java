package com.projectapp.controller;

import com.projectapp.service.VeiculoPneuService;
import com.projectapp.service.dto.VeiculoPneuDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/veiculos/{placa}/pneus")
@RequiredArgsConstructor
@Tag(name = "Veículo-Pneu", description = "API para gerenciamento de pneus por veículo")
public class VeiculoPneuController {
    
    private final VeiculoPneuService veiculoPneuService;

    @PostMapping("/{numeroFogo}/posicao/{posicao}")
    @Operation(summary = "Vincula um pneu a uma posição do veículo")
    public ResponseEntity<VeiculoPneuDTO> vincularPneu(
                                                        @PathVariable String placa,
                                                        @PathVariable String numeroFogo,
                                                        @PathVariable String posicao) {

        return ResponseEntity.ok(veiculoPneuService.vincularPneu(placa, numeroFogo, posicao));
    }
    
    @DeleteMapping("/{numeroFogo}")
    @Operation(summary = "Desvincula um pneu do veículo")
    public ResponseEntity<Void> desvincularPneu(
                                                @PathVariable String placa,
                                                @PathVariable String numeroFogo) {

        veiculoPneuService.desvincularPneu(placa, numeroFogo);
        return ResponseEntity.noContent().build();
    }
}

