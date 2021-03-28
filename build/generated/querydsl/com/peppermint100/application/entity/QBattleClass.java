package com.peppermint100.application.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBattleClass is a Querydsl query type for BattleClass
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBattleClass extends EntityPathBase<BattleClass> {

    private static final long serialVersionUID = -131480895L;

    public static final QBattleClass battleClass = new QBattleClass("battleClass");

    public final StringPath className = createString("className");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<User, QUser> userList = this.<User, QUser>createList("userList", User.class, QUser.class, PathInits.DIRECT2);

    public QBattleClass(String variable) {
        super(BattleClass.class, forVariable(variable));
    }

    public QBattleClass(Path<? extends BattleClass> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBattleClass(PathMetadata metadata) {
        super(BattleClass.class, metadata);
    }

}

