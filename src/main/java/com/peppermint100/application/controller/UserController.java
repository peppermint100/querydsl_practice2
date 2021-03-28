package com.peppermint100.application.controller;

import com.peppermint100.application.dto.UserDto;
import com.peppermint100.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping("/userPaging")
    public Page<UserDto> userPaging(Pageable pageable){
        return userRepository.pagingQuery(pageable);
    }

    @GetMapping("/userPagingWithoutCount")
    public Page<UserDto> userPagingWithoutCount(Pageable pageable){
        return userRepository.pagingQueryWithoutCount(pageable);
    }
}
