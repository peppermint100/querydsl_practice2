package com.peppermint100.application.repository;

import com.peppermint100.application.dto.UserDto;
import com.peppermint100.application.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepositoryCustom {
    Page<UserDto> pagingQuery(Pageable pageable);
    Page<UserDto> pagingQueryWithoutCount(Pageable pageable);
}
