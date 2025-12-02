package com.deca.PruebaTecnicaSuperMercado.repository;

import com.deca.PruebaTecnicaSuperMercado.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByName(String name);
}
