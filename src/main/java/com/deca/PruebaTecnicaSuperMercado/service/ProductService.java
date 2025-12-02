package com.deca.PruebaTecnicaSuperMercado.service;

import com.deca.PruebaTecnicaSuperMercado.dto.Product.ProductRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Product.ProductResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Product;
import com.deca.PruebaTecnicaSuperMercado.exception.BusinessRuleException;
import com.deca.PruebaTecnicaSuperMercado.exception.ResourceNotFoundException;
import com.deca.PruebaTecnicaSuperMercado.mapper.ProductMapper;
import com.deca.PruebaTecnicaSuperMercado.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        // Obtenemos todas las entidades de la base de datos y las convertimos a DTOs
        return productRepository.findAll().stream().map(productMapper::toResponseDTO).toList();
    }

    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {

        // Convertir DTO a Entidad
        Product productEntity = productMapper.toEntity(productRequestDTO);

        // Verificar si ya existe un producto con el mismo nombre
        if (productRepository.findByName(productEntity.getName()).isPresent()) {
            throw new BusinessRuleException("El producto con el nombre " + productEntity.getName() + " ya existe.");
        }

        // Guardar en BD
        Product savedProduct = productRepository.save(productEntity);

        // Convertir la entidad guardada (que ya tiene ID) a ResponseDTO
        return productMapper.toResponseDTO(savedProduct);
    }

    @Transactional
    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {

        // Buscar la entidad existente por ID
        Product productEntity = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("El producto con el id " + id + " no existe."));

        // Actualizar la entidad con los datos del DTO
        productMapper.updateProductFromDTO(productRequestDTO, productEntity);

        // Guardar los cambios en la base de datos
        Product updatedProduct = productRepository.save(productEntity);

        // Convertir la entidad actualizada a ResponseDTO
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {

        // Verificar si el producto existe
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("El producto con el id " + id + " no existe.");
        }

        productRepository.deleteById(id);
    }
}
