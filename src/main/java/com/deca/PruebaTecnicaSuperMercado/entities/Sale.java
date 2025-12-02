package com.deca.PruebaTecnicaSuperMercado.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SaleStatus saleStatus = SaleStatus.PENDING;

    private double total;

    // Muchas ventas pertenece a una sucursal
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    // Una venta pertenece a uno o varios tickets
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();

    // Metodo helper para agregar tickets
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setSale(this); // Mantienes ambos lados sincronizados
    }

}
