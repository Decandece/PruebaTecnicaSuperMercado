package com.deca.PruebaTecnicaSuperMercado.dto.Sale;

import com.deca.PruebaTecnicaSuperMercado.dto.Ticket.TicketResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.SaleStatus;

import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDTO(
        Long saleId,
        LocalDateTime date,
        String branchName,
        SaleStatus status,
        List<TicketResponseDTO> items,
        Double total) { }
