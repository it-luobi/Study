package com.luobi.study.project.springboot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserUpdateDto {

    @ApiModelProperty(required = true)
    @NotBlank
    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    private String modifiedUserId;

}
