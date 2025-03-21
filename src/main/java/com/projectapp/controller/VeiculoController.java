package com.projectapp.controller;

import com.projectapp.service.dto.VeiculoDTO;
import com.projectapp.service.dto.VeiculoResumoDTO;
import com.projectapp.service.impl.VeiculoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/veiculos")
@RequiredArgsConstructor
@Tag(name = "Veiculo", description = "API para gerenciamento dos veículos")
public class VeiculoController {
    
    private final VeiculoServiceImpl veiculoService;

    @GetMapping
    @Operation(summary = "Lista todos os veículos.")
    public ResponseEntity<List<VeiculoResumoDTO>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }
    
    @GetMapping("/{placa}")
    @Operation(summary = "Busca veículo pela placa.")
    public ResponseEntity<VeiculoDTO> buscarPorId(@PathVariable String placa) {
        return ResponseEntity.ok(veiculoService.buscarPorId(placa));
    }
    
    @PostMapping
    @Operation(summary = "Salvar veículo.")
    public ResponseEntity<VeiculoDTO> criar(@RequestBody VeiculoDTO dto) {
        return ResponseEntity.ok(veiculoService.salvar(dto));
    }

} 