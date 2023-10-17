package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient extends BaseEntity {
    @Id
    @GeneratedValue
    private Long ingredientId;

    @Column(nullable = false)
    private String ingredientName;

    @Column(nullable = false)
    private String weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;

    public void modifyIngredientName(String ingredientName){
        this.ingredientName = ingredientName;
    }

    public void modifyWeight(String weight){
        this.weight = weight;
    }


}
