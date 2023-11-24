package com.luobi.study.project.springboot.model;

import lombok.Data;

@Data
public class User {

    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    private String registeredDate;
    private String registeredUserId;
    private String modifiedDate;
    private String modifiedUserId;

}
