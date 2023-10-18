package com.example.jache.receipe.service.impl;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.receipe.dto.IngredientDto;
import com.example.jache.receipe.entity.Ingredient;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.repository.IngredientRepository;
import com.example.jache.receipe.repository.ReceipeRepository;
import com.example.jache.receipe.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final ReceipeRepository receipeRepository;
    private final IngredientRepository ingredientRepository;

    public boolean isAuthoirezed(Long ingredientId, String chefName){
        Ingredient ingredient = ingredientRepository.findByIngredientId(ingredientId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.INGREDIENT_NOT_FOUND)
        );
        Receipe receipe = ingredient.getReceipe();
        if(chefName.equals(receipe.getChef().getChefName())){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public IngredientDto.IngredientResDto addIngredient(IngredientDto.IngredientReqDto ingredientReqDto) {
        Receipe receipe = receipeRepository.findByReceipeId(ingredientReqDto.getReceipeId()).orElseThrow(
                ()->new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );
        Ingredient ingredient = Ingredient.builder()
                .ingredientName(ingredientReqDto.getIngredientName())
                .weight(ingredientReqDto.getWeight())
                .receipe(receipe)
                .build();
        ingredientRepository.save(ingredient);

        return new IngredientDto.IngredientResDto(ingredient);
    }

    @Override
    public void deleteIngredient(Long ingredientId) {

        Ingredient ingredient = ingredientRepository.findByIngredientId(ingredientId).orElseThrow(
                ()-> new CustomException(CustomResponseStatus.INGREDIENT_NOT_FOUND)
        );
        ingredientRepository.delete(ingredient);
    }

    @Override
    public Long updateIngredient(IngredientDto.UpdateIngredientReqDto update, Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findByIngredientId(ingredientId).orElseThrow(
                ()-> new CustomException(CustomResponseStatus.INGREDIENT_NOT_FOUND)
        );

        if(!ingredient.getIngredientName().equals(update.getIngredientName())){
            ingredient.modifyIngredientName(update.getIngredientName());
        }
        if(!ingredient.getWeight().equals(update.getWeight())){
            ingredient.modifyWeight(update.getWeight());
        }
        ingredientRepository.save(ingredient);
        return ingredient.getIngredientId();
    }
}
