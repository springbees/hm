package com.jacklinsir.hm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/6 19:11
 */
@Slf4j
@Controller
@Api(tags = "SecurityController", description = "SpringSecurity页面跳转")
public class SecurityController {


    @ApiOperation("登入页面")
    @GetMapping(value = "/login.html")
    public String login() {
        return "login";
    }

    @ApiOperation("403权限不足页面")
    @GetMapping(value = "/403.html")
    public String noPermission() {
        return "403";
    }
}
