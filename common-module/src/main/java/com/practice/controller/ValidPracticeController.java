package com.practice.controller;

import com.practice.domain.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("valid")
@Api(tags = "校验注解相关接口")
public class ValidPracticeController {

    @PostMapping("/test")
    @ApiOperation("测试校验注解")
    public String test(@Valid @RequestBody UserQuery userQuery) {
        // TODO 业务逻辑
        return "success";
    }

    @GetMapping("/get")
    public String get() {
        // TODO 业务逻辑
        return "success";
    }
}
