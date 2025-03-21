package com.projectapp.controller;

import com.projectapp.service.PneuService;
import com.projectapp.service.dto.PneuDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pneus")
@RequiredArgsConstructor
@Tag(name = "Pneu", description = "API para gerenciamento de pneus")
public class PneuController {

    private final PneuService pneuService;

    @GetMapping
    @Operation(summary = "Lista todos os pneus")
    public ResponseEntity<List<PneuDTO>> listarTodos() {
        return ResponseEntity.ok(pneuService.listarTodos());
    }

    @GetMapping("/{numeroFogo}")
    @Operation(summary = "Busca um pneu pelo número de fogo")
    public ResponseEntity<PneuDTO> buscarPorNumeroFogo(@PathVariable String numeroFogo) {
        return ResponseEntity.ok(pneuService.buscarPorNumeroFogo(numeroFogo));
    }

    @PostMapping
    @Operation(summary = "Cria um novo pneu")
    public ResponseEntity<PneuDTO> salvar(@RequestBody PneuDTO pneuDTO) {
        return ResponseEntity.ok(pneuService.salvar(pneuDTO));
    }
} 