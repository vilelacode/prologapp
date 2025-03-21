package com.projectapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "veiculo_pneu")
@EqualsAndHashCode(of = "id")
public class VeiculoPneu {

    @EmbeddedId
    private VeiculoPneuId id;

} 