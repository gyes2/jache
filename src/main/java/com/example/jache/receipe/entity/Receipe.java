package com.example.jache.receipe.entity;


import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Receipe {
    @Id @GeneratedValue
    private Long receipeId;
    private String theme;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chefId")
    private Chef chef;

    @OneToMany(mappedBy = "receipe")
    private List<love> loves = new ArrayList<>();

    @OneToMany(mappedBy = "receipe")
    private List<Ingredient> ingredients= new ArrayList<>();

    @OneToMany(mappedBy = "receipe")
    private List<Orders> orders = new ArrayList<>();

    @OneToMany(mappedBy = "receipe")
    private List<ReceipeImgUrl> receipeImgUrls = new ArrayList<>();

    @Embedded
    private BaseEntity baseEntity;
}
