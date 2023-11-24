package com.luobi.study.project.springboot.controller;

import com.luobi.study.project.springboot.dto.SwaggerTestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/swagger-test")
// 说明接口文件
@Api(value = "测试接口", tags = "Swagger测试接口", description = "用于测试Swagger")
public class SwaggerController {

    /**
     * 保存数据
     */
    @PostMapping(value = "/save")
    // 方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParam(name = "user", value = "新增用户数据")
    // 说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "添加用户", notes = "添加用户，id不能为空")
    public String saveUser(SwaggerTestDto dto) {
        return "保存成功: " + dto;
    }

    /**
     * 根据id查询用户
     */
    @GetMapping(value = "findById")
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id查询用户信息")
    public SwaggerTestDto getUser(String id) {
        SwaggerTestDto dto = new SwaggerTestDto();
        dto.setId(id);
        dto.setName("luobi");
        dto.setAge(24);
        return dto;
    }

    @DeleteMapping(value = "deleteById")
    @ApiOperation(value = "根据id删除数据", notes = "删除用户")
    public String delete(String id) {
        return "删除成功: " + id;
    }

    @GetMapping("/testFunction")
    @ApiOperation(value = "功能测试", notes = "launchStatusCode 和 lifeCycleCode 是必需的，且只能传其中一个参数")
    public String testFunction(SwaggerTestDto.TestFunctionDto dto) {
        String lifeCycleCode = dto.getLifeCycleCode();
        String launchStatusCode = dto.getLaunchStatusCode();

        if (StringUtils.isEmpty(lifeCycleCode) && StringUtils.isEmpty(launchStatusCode)) {
            return "不能两个参数都为空！";
        }
        if (StringUtils.isNoneBlank(lifeCycleCode) && StringUtils.isNoneBlank(launchStatusCode)) {
            return "不能同时传两个参数！";
        }

        return dto.toString();
    }

    @PostMapping("/testList")
    @ApiOperation(value = "List测试")
    public Object testList(@RequestBody List<String> memberNoList) {
        if (CollectionUtils.isEmpty(memberNoList)) {
            return null;
        }

        return StringUtils.join(memberNoList);
    }

}
