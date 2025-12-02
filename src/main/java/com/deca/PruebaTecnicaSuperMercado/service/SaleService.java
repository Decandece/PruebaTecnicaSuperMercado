package com.deca.PruebaTecnicaSuperMercado.service;

import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Sale.SaleResponseDTO;
import com.deca.PruebaTecnicaSuperMercado.dto.Ticket.TicketRequestDTO;
import com.deca.PruebaTecnicaSuperMercado.entities.Branch;
import com.deca.PruebaTecnicaSuperMercado.entities.Product;
import com.deca.PruebaTecnicaSuperMercado.entities.Sale;
import com.deca.PruebaTecnicaSuperMercado.entities.SaleStatus;
import com.deca.PruebaTecnicaSuperMercado.entities.Ticket;
import com.deca.PruebaTecnicaSuperMercado.exception.BusinessRuleException;
import com.deca.PruebaTecnicaSuperMercado.exception.ResourceNotFoundException;
import com.deca.PruebaTecnicaSuperMercado.mapper.SaleMapper;
import com.deca.PruebaTecnicaSuperMercado.repository.BranchRepository;
import com.deca.PruebaTecnicaSuperMercado.repository.ProductRepository;
import com.deca.PruebaTecnicaSuperMercado.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService implements ISaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleMapper saleMapper;

    @Override
    public List<SaleResponseDTO> getAllSales() {

        // Obtenemos todas las entidades de la base de datos y las convertimos a DTOs
        return saleRepository.findAll().stream().map(saleMapper::toResponseDTO).toList();

    }

    @Transactional
    @Override
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO) {
        // 1. Buscar la sucursal por ID
        Branch branch = branchRepository.findById(saleRequestDTO.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La sucursal con id " + saleRequestDTO.getBranchId() + " no existe."));

        // 2. Crear la entidad Sale a partir del DTO (sin branch ni tickets aún)
        Sale sale = saleMapper.toEntity(saleRequestDTO);
        sale.setBranch(branch); // Asignar la sucursal
        sale.setDate(LocalDateTime.now()); // Fecha actual
        sale.setSaleStatus(SaleStatus.PENDING); // Estado inicial

        double total = 0.0;

        // 3. Procesar cada item (TicketRequestDTO) para crear Tickets
        for (TicketRequestDTO ticketDTO : saleRequestDTO.getItems()) {
            // Buscar el producto por ID
            Product product = productRepository.findById(ticketDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "El producto con id " + ticketDTO.getProductId() + " no existe."));

            // Validar stock disponible
            if (product.getAmount() < ticketDTO.getQuantity()) {
                throw new BusinessRuleException("Stock insuficiente para el producto " + product.getName() +
                        ". Disponible: " + product.getAmount() + ", Solicitado: " + ticketDTO.getQuantity());
            }

            // Crear el Ticket
            Ticket ticket = Ticket.builder()
                    .product(product)
                    .quantity(ticketDTO.getQuantity())
                    .price(product.getPrice() * ticketDTO.getQuantity()) // Precio unitario * cantidad
                    .build();

            // Agregar el ticket a la venta (mantiene sincronización bidireccional)
            sale.addTicket(ticket);

            // Actualizar stock del producto
            product.setAmount(product.getAmount() - ticketDTO.getQuantity());

            // Sumar al total
            total += ticket.getPrice();
        }

        // 4. Asignar el total calculado
        sale.setTotal(total);

        // 5. Guardar la venta (con cascada, guarda también los tickets)
        Sale savedSale = saleRepository.save(sale);

        // 6. Convertir la entidad guardada a ResponseDTO y retornar
        return saleMapper.toResponseDTO(savedSale);
    }

    @Transactional
    @Override
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO saleRequestDTO) {

        Sale saleEntity = saleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("La sale con id " + id + " no existe."));

        if (saleEntity.getSaleStatus() == SaleStatus.COMPLETED) {
            throw new BusinessRuleException("La venta con id " + id + " ya está COMPLETADA y no puede ser modificada.");
        }

        Branch branch = branchRepository.findById(saleRequestDTO.getBranchId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "La sucursal con id " + saleRequestDTO.getBranchId() + " no existe."));

        saleEntity.setBranch(branch);

        // Devolver stock de los tickets anteriores antes de limpiarlos
        for (Ticket oldTicket : saleEntity.getTickets()) {
            Product oldProduct = oldTicket.getProduct();
            oldProduct.setAmount(oldProduct.getAmount() + oldTicket.getQuantity());
        }

        // Limpiar los tickets existentes
        saleEntity.getTickets().clear();

        double total = 0.0;

        for (TicketRequestDTO ticketDTO : saleRequestDTO.getItems()) {

            Product product = productRepository.findById(ticketDTO.getProductId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "El producto con id " + ticketDTO.getProductId() + " no existe."));

            // Validar stock disponible
            if (product.getAmount() < ticketDTO.getQuantity()) {
                throw new BusinessRuleException("Stock insuficiente para el producto " + product.getName() +
                        ". Disponible: " + product.getAmount() + ", Solicitado: " + ticketDTO.getQuantity());
            }

            Ticket ticket = Ticket.builder()
                    .product(product)
                    .quantity(ticketDTO.getQuantity())
                    .price(product.getPrice() * ticketDTO.getQuantity())
                    .build();

            saleEntity.addTicket(ticket);

            // Actualizar stock del producto
            product.setAmount(product.getAmount() - ticketDTO.getQuantity());

            total += ticket.getPrice();
        }

        saleEntity.setTotal(total);

        Sale updatedSale = saleRepository.save(saleEntity);

        return saleMapper.toResponseDTO(updatedSale);
    }

    @Transactional
    @Override
    public void deleteSale(Long id) {

        Sale saleEntity = saleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("La venta con id " + id + " no existe."));


        for (Ticket oldTicket : saleEntity.getTickets()) {
            Product oldProduct = oldTicket.getProduct();
            oldProduct.setAmount(oldProduct.getAmount() + oldTicket.getQuantity());
        }

        saleRepository.deleteById(id);

    }
}
