package com.deca.PruebaTecnicaSuperMercado.mapper;

import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TicketMapper.class)
public interface SaleMapper {

    // Entidad a DTO (Response)
    @Mapping(target = "saleId", source = "id")
    @Mapping(target = "branchName", source = "branch.name")
    @Mapping(target = "status", source = "saleStatus")
    @Mapping(target = "items", source = "tickets")
    SaleResponseDTO toResponseDTO(Sale sale);

    // DTO (REQUEST) A ENTIDAD
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "saleStatus", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Sale toEntity(SaleRequestDTO saleRequestDTO);

}
