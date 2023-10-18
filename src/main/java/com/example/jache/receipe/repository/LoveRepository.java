package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Love;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.user.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love,Long>, LoveRepositoryCustom {

}
