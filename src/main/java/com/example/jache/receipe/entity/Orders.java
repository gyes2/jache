package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;
    private String content;
    @Column(nullable = false)
    private String ContentUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;
}
