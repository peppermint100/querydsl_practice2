package com.peppermint100.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "USER_TABLE")
@Getter @Setter
@ToString(of = {"id", "username", "level"})
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_class_id")
    private BattleClass battleClass;

    public User(String username, Integer level, BattleClass battleClass) {
        this.username = username;
        this.level = level;
        changeBattleClass(battleClass);
    }

    private void changeBattleClass(BattleClass battleClass){
        this.battleClass = battleClass;
        battleClass.getUserList().add(this);
    }
}
