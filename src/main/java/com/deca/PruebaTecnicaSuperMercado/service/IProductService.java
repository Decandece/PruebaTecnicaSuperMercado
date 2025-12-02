package com.deca.PruebaTecnicaSuperMercado.service;

import com.deca.PruebaTecnicaSuperMercado.dto.Product.ProductRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Product.ProductResponseDTO;

import java.util.List;

public interface IProductService {

    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long id);
}
