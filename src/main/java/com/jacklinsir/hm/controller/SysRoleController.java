package com.jacklinsir.hm.controller;

import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.model.SysRole;
import com.jacklinsir.hm.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/2 21:11
 */
@Slf4j
@Controller
@RequestMapping("role")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;

    @ResponseBody
    @PostMapping("/all")
    public CommonResults list() {
        if (roleService.roleAll().isEmpty()) {
            return CommonResults.failure();
        }
        return CommonResults.success(0, roleService.roleAll());
    }
}
