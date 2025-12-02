package com.deca.PruebaTecnicaSuperMercado.repository;

import com.deca.PruebaTecnicaSuperMercado.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
