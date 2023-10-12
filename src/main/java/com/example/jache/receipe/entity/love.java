package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;

@Entity
public class love extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chefId")
    private Chef chef;

    private String status;
}
