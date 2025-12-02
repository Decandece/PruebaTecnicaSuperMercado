package com.deca.PruebaTecnicaSuperMercado.dto.Sale;

import com.deca.PruebaTecnicaSuperMercado.dto.Ticket.TicketRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequestDTO {

    @NotNull(message = "The branchId cannot be null")
    private Long branchId;

    @NotNull(message = "The items list cannot be null")
    @NotEmpty(message = "The items list cannot be empty")
    @Valid
    private List<TicketRequestDTO> items;

}
