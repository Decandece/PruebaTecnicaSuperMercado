package com.deca.PruebaTecnicaSuperMercado.controller;

import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.service.IBranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {

    @Autowired
    private IBranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@Valid @RequestBody BranchRequestDTO branchRequestDTO) {

        BranchResponseDTO creado = branchService.createBranche(branchRequestDTO);

        return ResponseEntity.created(URI.create("/api/v1/branches/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(@PathVariable Long id, @Valid @RequestBody BranchRequestDTO branchRequestDTO) {
        return ResponseEntity.ok(branchService.updateBranche(id, branchRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {

        branchService.deleteBranche(id);
        return ResponseEntity.noContent().build();

    }
}
