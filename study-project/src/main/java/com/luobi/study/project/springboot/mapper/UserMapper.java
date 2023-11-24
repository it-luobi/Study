package com.luobi.study.project.springboot.mapper;

import com.luobi.study.project.springboot.dto.UserListQueryDto;
import com.luobi.study.project.springboot.dto.UserRegisterDto;
import com.luobi.study.project.springboot.dto.UserUpdateDto;
import com.luobi.study.project.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getUserList(UserListQueryDto userListDto);

    int getUserCount(UserListQueryDto userListDto);

    User getUserById(String userId);

    void addUser(UserRegisterDto userRegisterDto);

    void updateUser(UserUpdateDto userUpdateDto);

    void deleteUser(List<String> userIdList);

}
