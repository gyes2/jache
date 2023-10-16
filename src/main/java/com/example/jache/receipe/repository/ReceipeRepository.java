package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Receipe;
import com.example.jache.user.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceipeRepository extends JpaRepository<Receipe,Long> {

    Optional<Receipe> findByReceipeId(Long receipeId);

    Optional<Receipe> findReceipeByChef(Chef chef);
}
