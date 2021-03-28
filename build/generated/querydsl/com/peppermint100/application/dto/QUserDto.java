package com.peppermint100.application.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * com.peppermint100.application.dto.QUserDto is a Querydsl Projection type for UserDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QUserDto extends ConstructorExpression<UserDto> {

    private static final long serialVersionUID = 1380472217L;

    public QUserDto(com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<Integer> level, com.querydsl.core.types.Expression<String> className) {
        super(UserDto.class, new Class<?>[]{String.class, int.class, String.class}, username, level, className);
    }

}

