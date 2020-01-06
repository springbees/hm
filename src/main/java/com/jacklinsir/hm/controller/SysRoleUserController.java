package com.jacklinsir.hm.controller;

import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.model.SysRoleUser;
import com.jacklinsir.hm.service.SysRoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用户角色控制器)
 * @Date 2020/1/4 16:08
 */
@Slf4j
@Controller
@RequestMapping("roles_user")
@Api(tags = "SysRoleUserController", description = "角色与用户关联")
public class SysRoleUserController {

    @Autowired
    private SysRoleUserService roleUserService;

    @ApiOperation("根据用户ID查询用户角色关联表数据")
    @ResponseBody
    @PostMapping("/getRoleUserByUserId")
    public CommonResults getRoleUser(@RequestParam("userId") Integer userId) {
        try {
            SysRoleUser user = roleUserService.getRoleUserByUserId(userId);
            return CommonResults.success(ResponseCode.SUCCESS, user);
        } catch (Exception e) {
            log.error("根据用户ID查询角色出异常了: {}", e.fillInStackTrace());
            return CommonResults.success(e.getMessage());
        }
    }
}
