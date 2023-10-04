package com.example.jache.constant.entity;


import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class BaseEntity {
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

}
