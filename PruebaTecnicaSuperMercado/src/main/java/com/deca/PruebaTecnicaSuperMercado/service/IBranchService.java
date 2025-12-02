package com.deca.PruebaTecnicaSuperMercado.service;

import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchResponseDTO;

import java.util.List;

public interface IBranchService {

    List<BranchResponseDTO> getAllBranches();

    BranchResponseDTO createBranche(BranchRequestDTO branchRequestDTO);

    BranchResponseDTO updateBranche(Long id,BranchRequestDTO branchRequestDTO);

    void deleteBranche(Long id);

}
