package com.example.jache.receipe.entity;


import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipe extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receipeId;

    @Column(nullable = false, length = 1)
    private String theme;

    @Column(nullable = false)
    private String title;

    private String introduce;

    @Column(nullable = false)
    private String receipeImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chefId")
    private Chef chef;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int loveCount;

    @OneToMany(mappedBy = "receipe", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Ingredient> ingredients= new ArrayList<>();

    @OneToMany(mappedBy = "receipe", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Orders> orders = new ArrayList<>();


    public void createReceipe(List<Ingredient> ingredients, List<Orders> orders){
        this.ingredients = ingredients;
        this.orders = orders;
    }

    public void modifyTitle(String title){
        this.title = title;
    }

    public void modifyTheme(String theme){
        this.theme = theme;
    }

    public void modifyIntroduce(String introduce){
        this.introduce = introduce;
    }

    public void modifyReceipeImgUrl(String receipeImgUrl){
        this.receipeImgUrl = receipeImgUrl;
    }

    public void addCount(){
        this.loveCount += 1;
    }

    public void subCount(){
        if(this.loveCount > 0){
            this.loveCount -= 1;
        }
    }
}
