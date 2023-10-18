package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Love;
import com.example.jache.receipe.entity.Receipe;

import java.util.List;

public interface ReceipeRepositoryCustom {

    List<Receipe> findReceipesByScrap(String chefName);
}
