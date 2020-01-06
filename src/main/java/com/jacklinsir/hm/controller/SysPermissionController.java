package com.jacklinsir.hm.controller;

import com.alibaba.fastjson.JSONArray;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.model.SysPermission;
import com.jacklinsir.hm.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (权限控制器)
 * @Date 2020/1/5 16:24
 */
@Api(tags = "SysPermissionController", description = "权限资源管理")
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
    @ApiOperation("获取权限列表")
    @ResponseBody
    @GetMapping("listAllPermission")
    public CommonResults<JSONArray> listAllPermission() {
        return permissionService.listAllPermission();
    }


    @ApiOperation("获取权限角色关联表列表")
    @ResponseBody
    @GetMapping("/listAllPermissionByRoleId")
    public CommonResults listAllPermissionByRoleId(@RequestParam("id") Integer id) {
        log.info(getClass().getName() + " : param =  " + id);
        return permissionService.listAllPermissionByRoleId(id);
    }

    @ApiOperation("获取菜单列表")
    @ResponseBody
    @GetMapping("/menuAll")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public CommonResults menuAll() {
        List<SysPermission> datas = permissionService.menuAll();
        return CommonResults.success(ResponseCode.SUCCESS, datas.size(), datas);
    }

    @ApiOperation("添加权限页面")
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("sysPermission", new SysPermission());
        return "permission/permission-add";
    }

    @ApiOperation("权限添加")
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public CommonResults add(@RequestBody SysPermission permission) {
        try {
            int save = permissionService.save(permission);
            if (save > 0) {
                return CommonResults.success(ResponseCode.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("保存权限资源异常: {}", e.getMessage());
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }
        return CommonResults.failure();
    }


    /**
     * 跳转编辑页面
     *
     * @param modelMap
     * @param id
     * @return
     */
    @ApiOperation("编辑权限页面")
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public String edit(ModelMap modelMap, @RequestParam("id") Integer id) {
        modelMap.addAttribute("sysPermission", permissionService.getSysPermissionById(id));
        return "permission/permission-add";
    }

    @ApiOperation("更新权限")
    @ResponseBody
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public CommonResults edit(@RequestBody SysPermission permission) {
        try {
            int updatePermission = permissionService.updatePermission(permission);
            if (updatePermission > 0) {
                return CommonResults.success(ResponseCode.SUCCESS);
            }
            return CommonResults.failure();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("保存权限资源异常: {}", e.getMessage());
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }

    }


    @ApiOperation("删除权限")
    @ResponseBody
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('sys:menu:del')")
    public CommonResults deleteById(@RequestParam("id") Integer id) {
        try {
            int delete = permissionService.deleteById(id);
            if (delete > 0) {
                return CommonResults.success();
            }
            return CommonResults.failure();
        } catch (Exception e) {
            log.info("删除权限出现异常：{}", e.getMessage());
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }
    }

    @ApiOperation("根据用户ID查询权限菜单")
    @ResponseBody
    @GetMapping("/menu")
    public CommonResults<SysPermission> getMenu(@RequestParam("userId") Integer userId) {
        return permissionService.getMenu(userId);
    }

}
