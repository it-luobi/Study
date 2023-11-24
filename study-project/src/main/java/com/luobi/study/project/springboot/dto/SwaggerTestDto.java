package com.luobi.study.project.springboot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwaggerTestDto {

    @ApiModelProperty(required = true, allowableValues = ("01, 02, 03"))
    @NotNull
    private String id;

    @ApiModelProperty(name = "name", value = "memberName")
    private String name;

    private Integer age;

    @ApiModelProperty(hidden = true)
    private String created;

    @Data
    public static class TestFunctionDto {
        @ApiModelProperty(allowableValues = "VENDOR_EOS,EOL,EOS")
        private String lifeCycleCode;
        @ApiModelProperty(allowableValues = "NORMAL, ABNORMAL")
        private String launchStatusCode;
    }

}
