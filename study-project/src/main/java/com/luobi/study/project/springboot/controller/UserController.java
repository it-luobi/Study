package com.luobi.study.project.springboot.controller;

import com.luobi.study.project.common.model.CommonResponse;
import com.luobi.study.project.common.model.PageData;
import com.luobi.study.project.common.model.PageRequest;
import com.luobi.study.project.springboot.dto.UserListQueryDto;
import com.luobi.study.project.springboot.dto.UserRegisterDto;
import com.luobi.study.project.springboot.dto.UserUpdateDto;
import com.luobi.study.project.springboot.model.User;
import com.luobi.study.project.springboot.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "用户管理", description = "用户管理接口")
public class UserController {

    private final UserService userService;

    /**
     * 查询User列表
     */
    @GetMapping("/list")
    public CommonResponse<PageData<User>> getUserList(UserListQueryDto userListDto, PageRequest pageRequest) {
        return CommonResponse.success(userService.getUserList(userListDto, pageRequest));
    }

    /**
     * 根据id查询User
     */
    @GetMapping("/{userId}")
    public CommonResponse<User> getUserById(@PathVariable String userId) {
        return CommonResponse.success(userService.getUserById(userId));
    }

    /**
     * 新增User
     */
    @PostMapping("/add")
    public CommonResponse<User> addUser(@RequestBody UserRegisterDto dto) {
        return CommonResponse.success(userService.addUser(dto));
    }

    /**
     * 修改User信息
     */
    @PutMapping("/update")
    public CommonResponse<?> updateUser(@RequestBody @Valid UserUpdateDto dto) {
        userService.updateUser(dto);
        return CommonResponse.success();
    }

    /**
     * 根据id删除User
     */
    @DeleteMapping("/delete")
    public CommonResponse<?> deleteUser(@RequestBody List<String> userIdList) {
        userService.deleteUser(userIdList);
        return CommonResponse.success();
    }

}
