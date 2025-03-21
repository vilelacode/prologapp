package com.projectapp.repository;

import com.projectapp.entity.VeiculoPneu;
import com.projectapp.entity.VeiculoPneuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoPneuRepository extends JpaRepository<VeiculoPneu, VeiculoPneuId> {

    List<VeiculoPneu> findById_Veiculo_Placa(String placa);

    Optional<VeiculoPneu> findById_Veiculo_PlacaAndId_Pneu_NumeroFogo(String placa, String numeroFogo);

    boolean existsById_Veiculo_PlacaAndId_Posicao(String placa, String posicao);

    Optional<String> findId_PosicaoById_Veiculo_PlacaAndId_Pneu_NumeroFogo(String placa, String numeroFogo);
}