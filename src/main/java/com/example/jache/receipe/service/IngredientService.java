package com.example.jache.receipe.service;

import com.example.jache.receipe.dto.IngredientDto;

public interface IngredientService {

    public boolean isAuthoirezed(Long ingredientId, String chefName);

    IngredientDto.IngredientResDto addIngredient(IngredientDto.IngredientReqDto ingredientReqDto);

    void deleteIngredient(Long ingredientId);

    Long updateIngredient(IngredientDto.UpdateIngredientReqDto update, Long ingredientId);
}
