package com.deca.PruebaTecnicaSuperMercado.dto.Ticket;

public record TicketResponseDTO(
        Long ticketId,
        String productName,
        Integer quantity,
        Double unitPrice,
        Double subTotal) {
}
