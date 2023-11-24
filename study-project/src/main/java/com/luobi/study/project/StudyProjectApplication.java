package com.luobi.study.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyProjectApplication {

    // swagger地址：http://localhost:8085/project/swagger-ui.html
    public static void main(String[] args) {
        SpringApplication.run(StudyProjectApplication.class, args);
        System.out.println("(*^▽^*)启动成功!!!(〃'▽'〃)");
    }

}
