package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Love;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.user.entity.Chef;

import java.util.List;
import java.util.Optional;

public interface LoveRepositoryCustom {

    Love findLoveByReceipeIdAndChefName(Long receipeId, String chefName);
}
