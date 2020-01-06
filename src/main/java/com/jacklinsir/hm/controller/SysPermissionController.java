package com.jacklinsir.hm.controller;

import com.alibaba.fastjson.JSONArray;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author linSir
 * @version V1.0
 * @Description: (权限控制器)
 * @Date 2020/1/5 16:24
 */
@Slf4j
@Controller()
@RequestMapping("permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 查询所有权限
     *
     * @return
     */
    @ResponseBody
    @GetMapping("listAllPermission")
    public CommonResults<JSONArray> listAllPermission() {
        return permissionService.listAllPermission();
    }


    @ResponseBody
    @GetMapping("/listAllPermissionByRoleId")
    public CommonResults listAllPermissionByRoleId(@RequestParam("id") Integer id) {
        log.info(getClass().getName() + " : param =  " + id);
        return permissionService.listAllPermissionByRoleId(id);
    }
}
