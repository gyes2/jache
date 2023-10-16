package com.example.jache.user.entity;

import com.example.jache.chat.entity.Chat;
import com.example.jache.chat.entity.ChatRoom;
import com.example.jache.constant.entity.BaseEntity;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.entity.love;
import com.example.jache.user.entity.enums.Role;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Chef extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chefId;

    @Column(nullable = false, length = 45)
    private String chefName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 13)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String chefDetail;

    private String chefImgUrl;

    @OneToMany(mappedBy = "chef")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chef")
    private List<love> loves = new ArrayList<>();

    @OneToMany(mappedBy = "chef")
    private List<Receipe> receipes = new ArrayList<>();

    @OneToMany(mappedBy = "chef")
    private List<Chat> chats = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String refreshToken;
    /*
    UserDetail 부분
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.getRole()));

        return authorities;
    }

    @Override
    public String getUsername() {

        return this.chefName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void modifyRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}
