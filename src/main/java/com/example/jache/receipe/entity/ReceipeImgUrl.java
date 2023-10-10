package com.example.jache.receipe.entity;

import jakarta.persistence.*;

@Entity
public class ReceipeImgUrl {
    @Id @GeneratedValue
    private Long receipeImgId;

    @Column(nullable = false)
    private String receipeImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;


}
