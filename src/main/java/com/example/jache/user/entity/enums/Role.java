package com.example.jache.user.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Role {
    ROLE_ALL("ROLE_ALL"),
    ROLE_USER("ROLE_USER");

    String role;
    public String value(){
        return this.role;
    }
}
