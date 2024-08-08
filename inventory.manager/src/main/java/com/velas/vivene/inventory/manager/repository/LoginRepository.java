package com.velas.vivene.inventory.manager.repository;

import com.velas.vivene.inventory.manager.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {
}
