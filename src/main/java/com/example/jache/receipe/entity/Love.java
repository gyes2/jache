package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.user.entity.Chef;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Love extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    private Receipe receipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chefId")
    private Chef chef;

    private String status;
}

