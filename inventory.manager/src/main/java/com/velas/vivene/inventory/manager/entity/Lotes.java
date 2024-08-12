package com.velas.vivene.inventory.manager.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Lotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
}
