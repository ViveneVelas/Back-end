package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.PedidoLote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoLoteRepository extends JpaRepository<PedidoLote, Integer> {
    Optional<PedidoLote> findByPedidoId(Integer pedidoId);
}
