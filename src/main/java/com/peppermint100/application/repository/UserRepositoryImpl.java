package com.peppermint100.application.repository;

import com.peppermint100.application.dto.QUserDto;
import com.peppermint100.application.dto.UserDto;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

import static com.peppermint100.application.entity.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    @Autowired private JPAQueryFactory query;

    @Override
    public Page<UserDto> pagingQuery(Pageable pageable) {
        QueryResults<UserDto> fetchResults = query
                .select(new QUserDto(
                        user.username,
                        user.level,
                        user.battleClass.className
                ))
                .from(user)
                .where(user.level.goe(2))
                .orderBy(user.battleClass.className.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<UserDto> userList = fetchResults.getResults();
        long total = fetchResults.getTotal();

        return new PageImpl<>(userList, pageable, total);
    }

    @Override
    public Page<UserDto> pagingQueryWithoutCount(Pageable pageable) {
        List<UserDto> fetch = query
                .select(new QUserDto(
                        user.username,
                        user.level,
                        user.battleClass.className
                ))
                .from(user)
                .where(user.level.goe(2))
                .orderBy(user.battleClass.className.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<UserDto> countQuery = query
                .select(new QUserDto(
                        user.username,
                        user.level,
                        user.battleClass.className
                ))
                .from(user)
                .where(user.level.goe(2))
                .orderBy(user.battleClass.className.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(fetch, pageable, countQuery::fetchCount);
    }
}
