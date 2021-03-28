package com.peppermint100.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "BATTLE_CLASS_TABLE")
@Getter @Setter
@ToString(of = {"id", "className"})
@NoArgsConstructor
public class BattleClass {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "battle_class_id")
    private Long id;

    private String className;

    @OneToMany(mappedBy = "battleClass", fetch = FetchType.LAZY)
    private List<User> userList = new ArrayList<>();

    public BattleClass(String className) {
        this.className = className;
    }
}
