package com.deca.PruebaTecnicaSuperMercado.dto.Branch;

import java.io.Serializable;

public record BranchResponseDTO(
        Long id ,
        String name ,
        String address
) implements Serializable {}
