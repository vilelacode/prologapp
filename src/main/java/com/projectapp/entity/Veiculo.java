package com.projectapp.entity;

import com.projectapp.enumerated.StatusVeiculo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "veiculo")
@EqualsAndHashCode(of = "placa")
public class Veiculo {
    
    @Id
    @Column(nullable = false, unique = true)
    private String placa;
    
    @Column(nullable = false)
    private String marca;
    
    @Column(nullable = false)
    private Long quilometragem;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVeiculo status;

    @OneToMany(mappedBy = "id.veiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VeiculoPneu> pneus;

} 