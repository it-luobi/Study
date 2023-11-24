package com.luobi.study.project.springboot.mapper;

import com.google.common.collect.Lists;
import com.luobi.study.project.springboot.dto.UserListQueryDto;
import com.luobi.study.project.springboot.dto.UserRegisterDto;
import com.luobi.study.project.springboot.dto.UserUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserList() {
        UserListQueryDto dto = UserListQueryDto.builder()
                .id("100")
                .name("luobi")
                .gender("1")
                .age(25)
                .phone("15066668888")
                .email("15066668888@qq.com")
                .startDate("2023-11-01")
                .endDate("2023-12-01")
                .offset(0)
                .limit(5)
                .build();
        userMapper.getUserCount(dto);
        userMapper.getUserList(dto);
    }

    @Test
    public void getUserById() {
        userMapper.getUserById("100");
    }

    @Test
    public void addUser() {
        UserRegisterDto dto = UserRegisterDto.builder()
                .name("zhouYu")
                .gender("1")
                .age(29)
                .phone("15011113333")
                .email("15011113333@qq.com")
                .registeredUserId("100")
                .build();
        userMapper.addUser(dto);
    }

    @Test
    public void updateUser() {
        UserUpdateDto dto = UserUpdateDto.builder()
                .id("101")
                .name("zhaoYun")
                .gender("1")
                .age(30)
                .phone("17055557777")
                .email("17055557777@qq.com")
                .modifiedUserId("100")
                .build();
        userMapper.updateUser(dto);
    }

    @Test
    public void deleteUser() {
        List<String> userIdList = Lists.newArrayList();
        userIdList.add("111");
        userIdList.add("112");
        userMapper.deleteUser(userIdList);
    }

}
