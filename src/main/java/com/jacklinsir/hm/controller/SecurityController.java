package com.jacklinsir.hm.controller;

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
public class SecurityController {


    @GetMapping(value = "/login.html")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/403.html")
    public String noPermission() {
        return "403";
    }
}
