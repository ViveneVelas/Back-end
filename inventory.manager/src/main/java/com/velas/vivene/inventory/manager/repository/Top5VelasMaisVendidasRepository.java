package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.view.TopCincoVelas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Top5VelasMaisVendidasRepository extends JpaRepository<TopCincoVelas, Long> {
}
