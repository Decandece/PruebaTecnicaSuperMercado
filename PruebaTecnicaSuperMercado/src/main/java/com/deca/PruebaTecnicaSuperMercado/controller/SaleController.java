package com.deca.PruebaTecnicaSuperMercado.controller;

import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.service.ISaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAllSales() {

        return ResponseEntity.ok(saleService.getAllSales());

    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@Valid @RequestBody SaleRequestDTO saleRequestDTO) {

        SaleResponseDTO creado = saleService.createSale(saleRequestDTO);

        return ResponseEntity.created(URI.create("/api/v1/sales/" + creado.saleId())).body(creado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> updateSale(@PathVariable Long id, @Valid @RequestBody SaleRequestDTO saleRequestDTO) {
        SaleResponseDTO actualizado = saleService.updateSale(id, saleRequestDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

}
