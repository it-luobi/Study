package com.luobi.study.project.function;

import com.luobi.study.project.springboot.mapper.UserMapper;
import com.luobi.study.project.springboot.model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 复杂条件筛选测试
 * 要求：筛选出满足条件的 user
 * 1. user.name like '%ignoreCase(zh)%'
 * 2. user.email like '%{email type list}%'
 */
@SpringBootTest
public class FunctionTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFilterCondition() {
        List<User> userList = userMapper.getUserList(null);
        List<User> list = filterUser(userList);
        for (User user : list) {
            System.out.println(user);
        }
    }

    private List<User> filterUser(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }

        List<String> emailTypeList = new ArrayList<>();
        emailTypeList.add("@163.com");
        emailTypeList.add("@gmail.com");

        List<User> resultList = new ArrayList<>();
        for (User user : userList) {
            String name = user.getName();
            String email = user.getEmail();
            // 如果 name 和 email 都为空，必然不满足条件，跳过当前 user
            if (StringUtils.isAllBlank(name, email)) {
                continue;
            }

            // 如果 name 中包含 “zh” ，满足条件，遍历下一个 user
            if (StringUtils.isNotBlank(name) && name.toLowerCase().contains("zh")) {
                resultList.add(user);
                continue;
            }

            // 筛选出 email 地址中包含 @163.com 或 @gmail.com 的 user
            for (String emailType : emailTypeList) {
                if (StringUtils.isNotBlank(email) && email.toLowerCase().contains(emailType)) {
                    resultList.add(user);
                    break;
                }
            }
        }

        return resultList;
    }

}
