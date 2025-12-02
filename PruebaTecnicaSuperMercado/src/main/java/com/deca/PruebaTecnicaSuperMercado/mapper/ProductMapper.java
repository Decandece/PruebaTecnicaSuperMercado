package com.deca.PruebaTecnicaSuperMercado.mapper;

import com.deca.PruebaTecnicaSuperMercado.dto.Product.ProductRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Product.ProductResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // De entidad a dto (Response)
    ProductResponseDTO toResponseDTO(Product product);

    // DTO (REQUEST) A ENTIDAD
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductRequestDTO productRequestDTO);

    // Listado de DTO
    List<ProductResponseDTO> toResponseDTOList(List<Product> products);

    // Actualizar entidad desde DTO
    @Mapping(target = "id", ignore = true)
    void updateProductFromDTO(ProductRequestDTO dto, @MappingTarget Product entity);
}
