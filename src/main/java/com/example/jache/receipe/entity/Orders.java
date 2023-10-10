package com.example.jache.receipe.entity;

import jakarta.persistence.*;

@Entity
public class Orders {
    @Id @GeneratedValue
    private Long ordersId;
    private String content;
    @Column(nullable = false)
    private String ContentUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;
}
