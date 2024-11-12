package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p.dtPedido FROM Pedido p GROUP BY p.dtPedido ORDER BY p.dtPedido ASC")
    List<LocalDate> findAllDistinctOrderByDtPedidoAsc();

    @Query("SELECT p.dtPedido FROM Pedido p WHERE p.dtPedido >= CURRENT_DATE  GROUP BY p.dtPedido ORDER BY p.dtPedido ASC")
    List<LocalDate> findAllDistinctOrderByDtPedidoAscNow();
}
