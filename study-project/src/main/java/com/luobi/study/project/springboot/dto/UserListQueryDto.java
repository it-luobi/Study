package com.luobi.study.project.springboot.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListQueryDto {

    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String email;
    private String startDate;
    private String endDate;

    @ApiParam(hidden = true)
    private Integer offset;
    @ApiModelProperty(hidden = true)
    private Integer limit;

}
