package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.PedidoVela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoVelaRepository extends JpaRepository<PedidoVela, Integer> {
    Optional<PedidoVela> findByPedidoId(Integer pedidoId);
}
