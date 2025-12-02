package com.deca.PruebaTecnicaSuperMercado.dto.Branch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequestDTO {

    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    private String name;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String address;

}
