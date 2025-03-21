package com.projectapp.entity;

import com.projectapp.enumerated.StatusPneu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pneu")
@AllArgsConstructor
@EqualsAndHashCode(of = "numeroFogo")
@NoArgsConstructor
public class Pneu {
    
    @Id
    @Column(name = "numero_fogo", nullable = false)
    private String numeroFogo;
    
    @Column(nullable = false)
    private String marca;
    
    @Column(name = "pressao_atual", nullable = false)
    private Double pressaoAtual;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPneu status;

}