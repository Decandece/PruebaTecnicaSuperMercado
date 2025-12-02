package com.deca.PruebaTecnicaSuperMercado.service;

import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleResponseDTO;

import java.util.List;

public interface ISaleService {

    List<SaleResponseDTO> getAllSales();
    SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO);
    SaleResponseDTO updateSale(Long id,SaleRequestDTO saleRequestDTO);
    void deleteSale(Long id);
}
