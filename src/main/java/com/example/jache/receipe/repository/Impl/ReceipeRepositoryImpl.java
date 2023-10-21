package com.example.jache.receipe.repository.Impl;

import com.example.jache.receipe.entity.Love;
import com.example.jache.receipe.entity.QLove;
import com.example.jache.receipe.entity.QReceipe;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.repository.ReceipeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReceipeRepositoryImpl implements ReceipeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QReceipe receipe = new QReceipe("receipe");
    private final QLove love = new QLove("love");

    @Override
    public List<Receipe> findReceipesByScrap(String chefName) {
        return jpaQueryFactory.selectFrom(receipe)
                .join(love)
                .on(love.receipe.receipeId.eq(receipe.receipeId))
                .where(love.chef.chefName.eq(chefName))
                .fetch();
    }

}
