package com.deca.PruebaTecnicaSuperMercado.mapper;

import com.deca.PruebaTecnicaSuperMercado.dto.Ticket.TicketResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "ticketId", source = "id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "unitPrice", source = "product.price")
    @Mapping(target = "subTotal", source = "price")
    TicketResponseDTO toResponseDTO(Ticket ticket);
}