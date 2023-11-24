package com.luobi.study.project.springboot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDto {

    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    private String registeredUserId;

}
