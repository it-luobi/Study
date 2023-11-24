package com.luobi.study.project.springboot.service;


import com.luobi.study.project.common.model.PageData;
import com.luobi.study.project.common.model.PageRequest;
import com.luobi.study.project.springboot.dto.UserListQueryDto;
import com.luobi.study.project.springboot.dto.UserRegisterDto;
import com.luobi.study.project.springboot.dto.UserUpdateDto;
import com.luobi.study.project.springboot.mapper.UserMapper;
import com.luobi.study.project.springboot.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public PageData<User> getUserList(UserListQueryDto userListDto, PageRequest pageRequest) {
        int totalCount = userMapper.getUserCount(userListDto);
        if (totalCount <= 0) {
            return new PageData<>(Collections.emptyList(), pageRequest, 0);
        }

        if (pageRequest != null && !pageRequest.isSearchAll()) {
            userListDto.setOffset(pageRequest.getOffset());
            userListDto.setLimit(pageRequest.getLimit());
        } else {
            userListDto.setOffset(0);
            userListDto.setLimit(Integer.MAX_VALUE);
        }
        List<User> userList = userMapper.getUserList(userListDto);

        return new PageData<>(userList, pageRequest, totalCount);
    }

    public User getUserById(String userId) {
        return userMapper.getUserById(userId);
    }

    public User addUser(UserRegisterDto userRegisterDto) {
        userMapper.addUser(userRegisterDto);
        return userMapper.getUserById(userRegisterDto.getId());
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        userMapper.updateUser(userUpdateDto);
    }

    public void deleteUser(List<String> userIdList) {
        userMapper.deleteUser(userIdList);
    }

}
