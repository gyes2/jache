package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue
    private Long ingredientId;
    private String ingredientName;

    private String weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;
}
