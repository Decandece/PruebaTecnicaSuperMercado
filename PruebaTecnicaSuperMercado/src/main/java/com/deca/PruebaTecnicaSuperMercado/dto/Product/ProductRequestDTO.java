package com.deca.PruebaTecnicaSuperMercado.dto.Product;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotNull(message = "The field name cannot be null")
    @Size(min = 2 , max = 150 , message = "The field name must be between 2 and 150 characters")
    private String name;

    @NotNull
    private String category;

    @NotNull(message = "The field price cannot be null")
    @PositiveOrZero(message = "The price must be zero or positive")
    private Double price;

    @NotNull(message = "The field amount cannot be null")
    @Positive(message = "The amount must be positive")
    private int amount;

}
