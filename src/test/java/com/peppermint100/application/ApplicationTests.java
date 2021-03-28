package com.peppermint100.application;

import com.peppermint100.application.dto.QUserDto;
import com.peppermint100.application.dto.UserDto;
import com.peppermint100.application.entity.BattleClass;
import com.peppermint100.application.entity.User;
import com.peppermint100.application.repository.BattleClassRepository;
import com.peppermint100.application.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static com.peppermint100.application.entity.QBattleClass.battleClass;
import static com.peppermint100.application.entity.QUser.user;

@SpringBootTest(classes = Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTests {

    @Autowired private JPAQueryFactory query;
    @Autowired private BattleClassRepository battleClassRepository;
    @Autowired private UserRepository userRepository;

    @BeforeAll
    public void setUp(){
        BattleClass warrior = new BattleClass("전사");
        BattleClass archer = new BattleClass("궁수");
        BattleClass mage = new BattleClass("마법사");
        battleClassRepository.saveAll(List.of(warrior, archer, mage));

        User pepper = new User("pepper", 5, warrior);
        User flame = new User("flame", 10, warrior);
        User iksuo = new User("iksuo", 2, archer);
        User scott = new User("scott", 15, mage);
        User bob = new User("bob", 25, archer);
        User jane = new User("jane", 11, mage);
        User gorden = new User("gorden", 3, warrior);
        User kelly = new User("kelly", 9, archer);
        User mickey = new User("mickey", 1, mage);
        User ken = new User("ken", 13, warrior);
        userRepository.saveAll(List.of(pepper, flame, iksuo, scott, bob, jane, gorden, kelly, mickey, ken));
    }

    @Test
    public void is_there_10_characters(){
        List<User> allCharacters = query
                .selectFrom(user)
                .fetch();
        Assertions.assertEquals(10, allCharacters.size());
    }

    @Test
    public void users_level_under_10(){
        List<User> usersLevelUnder10 = query
                .selectFrom(user)
                .where(user.level.loe(10))
                .fetch();

        Assertions.assertEquals(6, usersLevelUnder10.size());
    }

    @Test
    public void highest_level_user(){
        List<User> userOrderedByLevel = query
                .selectFrom(user)
                .orderBy(user.level.desc())
                .fetch();

        Assertions.assertEquals(25, userOrderedByLevel.get(0).getLevel());
    }

    @Test
    public void paging_with_fetch_results(){
        QueryResults<User> fetchResults = query
                .selectFrom(user)
                .offset(0)
                .limit(3)
                .fetchResults();

        Long total = fetchResults.getTotal();
        Long offset = fetchResults.getOffset();
        Long limit = fetchResults.getLimit();

        System.out.println("total = " + total);
        System.out.println("offset = " + offset);
        System.out.println("limit = " + limit);

        Assertions.assertEquals(3, fetchResults.getResults().size());
    }

    @Test
    public void inner_join(){
        List<User> joinedWithBattleClass = query
                .selectFrom(user)
                .join(user.battleClass, battleClass)
                .fetch();

        joinedWithBattleClass.forEach(System.out::println);
    }

    @Test
    public void left_join_on(){
        List<User> allArchers = query
                .select(user)
                .from(user)
                .leftJoin(battleClass)
                .on(battleClass.className.eq("궁수"), user.level.loe(10), user.username.eq("somerandomname"))
                .fetch();

        Assertions.assertEquals(10, allArchers.size());
    }

    @Test
    public void sub_query(){
       //query.select(user.level.max()).from(user).fetch();
       List<User> maxLevelUser = query
                .selectFrom(user)
                .where(
                    user.level.in(
                        JPAExpressions
                        .select(user.level.max())
                        .from(user)
                    )
                )
                .fetch();

       Assertions.assertEquals(25, maxLevelUser.get(0).getLevel());
    }

    @Test
    public void single_row(){
        List<Tuple> tupleBob = query
                .select(
                        user.username,
                        user.level,
                        user.battleClass
                )
                .from(user)
                .where(user.username.eq("bob"))
                .fetch();

        List<UserDto> bob = query
                .select(new QUserDto(
                        user.username,
                        user.level,
                        user.battleClass
                ))
                .from(user)
                .where(user.username.eq("bob"))
                .fetch();
        Assertions.assertEquals(1, bob.size());
        Assertions.assertEquals("bob", bob.get(0).getUsername());
    }

    @Test
    public void dynamic_query(){
        List<User> results1 = findByClassNameAndLevelUnder("마법사", 10);
        List<User> results2 = findByClassNameAndLevelUnderWithBooleanExpression("마법사", 10);
        Assertions.assertEquals(results1.get(0).getUsername(), results2.get(0).getUsername());
    }

    private BooleanExpression findByClassName(String className){
        return Objects.isNull(className) ? null : user.battleClass.className.eq(className);
    }

    private BooleanExpression findByLevelUnder(Integer level){
        return level < 0 ? null : user.level.lt(level);
    }

    private List<User> findByClassNameAndLevelUnderWithBooleanExpression(String className, Integer level){
        return query
                .selectFrom(user)
                .where(findByClassName(className), findByLevelUnder(level))
                .fetch();
    }

    private List<User> findByClassNameAndLevelUnder(String className, Integer level){
        BooleanBuilder builder = new BooleanBuilder();

        if(!Objects.isNull(className)){
            builder.and(user.battleClass.className.eq(className));
        }

        if(level > 0){
            builder.and(user.level.lt(level));
        }

        List<User> results = query
                .selectFrom(user)
                .where(builder)
                .fetch();

        return results;
    }
}
