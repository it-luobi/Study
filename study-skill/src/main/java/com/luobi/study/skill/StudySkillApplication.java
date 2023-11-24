package com.luobi.study.skill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling  // 开启定时任务
@SpringBootApplication
public class StudySkillApplication {

    // swagger地址：http://localhost:8086/skill/swagger-ui.html
    public static void main(String[] args) {
        SpringApplication.run(StudySkillApplication.class, args);
        System.out.println("(*^▽^*)启动成功!!!(〃'▽'〃)");
    }

}
