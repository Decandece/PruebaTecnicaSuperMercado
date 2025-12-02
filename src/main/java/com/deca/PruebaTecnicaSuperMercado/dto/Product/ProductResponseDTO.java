package com.deca.PruebaTecnicaSuperMercado.dto.Product;

import java.io.Serializable;

public record ProductResponseDTO(
        Long id,
        String name,
        String category,
        Double price,
        int amount
) implements Serializable {}
