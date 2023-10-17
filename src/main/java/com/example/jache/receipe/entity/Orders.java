package com.example.jache.receipe.entity;

import com.example.jache.constant.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    private String content;

    private String contentUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipeId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Receipe receipe;

    public void modifyContent(String content){
        this.content = content;
    }

    public void modifyContentUrl(String contentUrl){
        this.contentUrl = contentUrl;
    }
}
