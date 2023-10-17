package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Ingredient;
import com.example.jache.receipe.entity.Receipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByIngredientId(Long ingredientId);

    List<Ingredient> findIngredientsByReceipe(Sort sort, Receipe receipe);

}
