package com.peppermint100.application.dto;

import com.peppermint100.application.entity.BattleClass;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private Integer level;
    private String className;

    @QueryProjection
    public UserDto(String username, Integer level, String className) {
        this.username = username;
        this.level = level;
        this.className = className;
    }
}
