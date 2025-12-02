package com.deca.PruebaTecnicaSuperMercado.mapper;

import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Branch.BranchResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    // Entity a DTO (Response)
    BranchResponseDTO toResponseDTO(Branch branch);

    // Dto a Entidad (REQUEST)
    @Mapping(target = "id", ignore = true)
    Branch toEntity(BranchRequestDTO branchRequestDTO);

    void updateBranchFromDTO(BranchRequestDTO dto, @MappingTarget Branch entity);

}
