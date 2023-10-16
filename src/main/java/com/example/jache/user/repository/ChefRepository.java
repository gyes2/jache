package com.example.jache.user.repository;

import com.example.jache.user.entity.Chef;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long>, ChefRepositoryCustom {

    Optional<Chef> findByChefName(String chefName);
    Optional<Chef> findChefByEmail(String email);

    Optional<Chef> findByEmail(String email);

    Optional<Chef> findByRefreshToken(String token);
}
