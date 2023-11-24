package com.luobi.study.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luobi.study.project.springboot.dto.UserListQueryDto;
import com.luobi.study.project.springboot.mapper.UserMapper;
import com.luobi.study.project.springboot.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class StudyProjectApplicationTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() throws JsonProcessingException {

        //从redis缓存中获得指定的数据
        String userListData = redisTemplate.boundValueOps("userList").get();
        //如果redis中没有数据的话
        if (null == userListData) {
            //查询数据库获得数据
            UserListQueryDto dto = UserListQueryDto.builder().age(27).build();
            List<User> userList = userMapper.getUserList(dto);
            //转换成json格式字符串
            ObjectMapper om = new ObjectMapper();
            userListData = om.writeValueAsString(userList);
            //将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
            redisTemplate.boundValueOps("userList").set(userListData);
            System.out.println("==================== 从数据库获得数据 ====================");
        } else {
            System.out.println("==================== 从redis缓存中获得数据 ====================");
        }

        System.out.println(userListData);
    }

}
