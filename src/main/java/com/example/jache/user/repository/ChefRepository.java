package com.example.jache.user.repository;

import com.example.jache.user.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {

    Chef findChefByChefName(String chefName);
}
