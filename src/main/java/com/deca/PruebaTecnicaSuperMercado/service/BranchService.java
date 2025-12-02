package com.deca.PruebaTecnicaSuperMercado.service;

import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Branch;
import com.deca.PruebaTecnicaSuperMercado.exception.BusinessRuleException;
import com.deca.PruebaTecnicaSuperMercado.exception.ResourceNotFoundException;
import com.deca.PruebaTecnicaSuperMercado.mapper.BranchMapper;
import com.deca.PruebaTecnicaSuperMercado.repository.BranchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchMapper branchMapper;

    @Override
    public List<BranchResponseDTO> getAllBranches() {
        return branchRepository.findAll().stream().map(branchMapper::toResponseDTO).toList();
    }

    @Transactional
    @Override
    public BranchResponseDTO createBranche(BranchRequestDTO branchRequestDTO) {
        // 1. Convertir DTO a Entidad
        Branch branchEntity = branchMapper.toEntity(branchRequestDTO);

        if (branchRepository.findByName(branchEntity.getName()).isPresent()) {
            throw new BusinessRuleException("La sucursal con el nombre " + branchEntity.getName() + " ya existe.");
        }

        // 2. Guardar en BD
        Branch savedBranch = branchRepository.save(branchEntity);

        // 3. Convertir la entidad guardada (que ya tiene ID) a ResponseDTO
        return branchMapper.toResponseDTO(savedBranch);
    }

    @Transactional
    @Override
    public BranchResponseDTO updateBranche(Long id, BranchRequestDTO branchRequestDTO) {

        Branch branchEntity = branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("La sucursal con id " + id + " no existe."));

        branchMapper.updateBranchFromDTO(branchRequestDTO, branchEntity);

        Branch updateBranch = branchRepository.save(branchEntity);

        return branchMapper.toResponseDTO(updateBranch);

    }

    @Transactional
    @Override
    public void deleteBranche(Long id) {

        Branch branchEntity = branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("La sucursal con id " + id + " no existe."));

        branchRepository.delete(branchEntity);

    }
}
