package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Receipe;
import com.example.jache.user.entity.Chef;

import java.util.List;

public interface LoveRepositoryCustom {

    List<Receipe> findReceipesByScrap(String chefName);
}
