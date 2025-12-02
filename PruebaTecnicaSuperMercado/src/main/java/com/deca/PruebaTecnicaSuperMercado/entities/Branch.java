package com.deca.PruebaTecnicaSuperMercado.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;

    // Una sucursal tiene muchas ventas
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Sale> sales = new ArrayList<>();

    private void addSale(Sale sale) {
        sales.add(sale);
        sale.setBranch(this); // Mantienes ambos lados sincronizados
    }

}
