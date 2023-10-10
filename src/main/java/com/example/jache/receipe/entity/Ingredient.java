package com.example.jache.receipe.entity;

import jakarta.persistence.*;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue
    private Long ingredientId;
    private String ingredientName;

    private String weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;
}
