package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class ReceipeImgUrl extends BaseEntity {
    @Id @GeneratedValue
    private Long receipeImgId;

    @Column(nullable = false)
    private String receipeImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;


}
