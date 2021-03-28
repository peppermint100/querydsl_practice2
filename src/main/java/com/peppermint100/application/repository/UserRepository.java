package com.peppermint100.application.repository;

import com.peppermint100.application.dto.UserDto;
import com.peppermint100.application.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom{
    @Override
    Page<UserDto> pagingQuery(Pageable pageable);

    @Override
    Page<UserDto> pagingQueryWithoutCount(Pageable pageable);
}
