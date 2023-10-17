package com.example.jache.receipe.repository.Impl;

import com.example.jache.receipe.entity.QLove;
import com.example.jache.receipe.entity.QReceipe;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.repository.LoveRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class LoveRepositoryImpl implements LoveRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final QReceipe receipe = new QReceipe("receipe");
    private final QLove love = new QLove("love");

    @Override
    public List<Receipe> findReceipesByScrap(String chefName) {
        return jpaQueryFactory.selectFrom(receipe)
                .join(love.chef)
                .where(receipe.chef.chefName.eq(love.chef.chefName.eq(chefName).stringValue()))
                .fetch();
    }
}
