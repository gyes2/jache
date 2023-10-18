package com.example.jache.user.repository;

import com.example.jache.user.entity.QChef;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChefRepositoryImpl implements ChefRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private static final QChef chef = new QChef("chef");

    @Override
    public Long getChefIdByChefName(String chefName) {

        return jpaQueryFactory.select(chef.chefId)
                .where(chef.chefName.eq(chefName))
                .fetchOne();
    }
}
