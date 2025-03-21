package com.projectapp.repository;

import com.projectapp.entity.Pneu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PneuRepository extends JpaRepository<Pneu, String> {
    Pneu findByNumeroFogo(String pneuNumeroFogo);
} 