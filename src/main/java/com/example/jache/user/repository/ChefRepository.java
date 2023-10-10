package com.example.jache.user.repository;

import com.example.jache.user.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {

    Optional<Chef> findChefByChefName(String chefName);
    Optional<Chef> findChefByEmail(String email);
}
