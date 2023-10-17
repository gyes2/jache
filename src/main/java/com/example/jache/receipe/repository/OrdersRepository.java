package com.example.jache.receipe.repository;

import com.example.jache.receipe.entity.Orders;
import com.example.jache.receipe.entity.Receipe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findOrdersByReceipe(Sort sort, Receipe receipe);
}
