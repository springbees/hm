package com.jacklinsir.hm.controller;

import com.jacklinsir.hm.common.result.CommonPage;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.dto.RoleDto;
import com.jacklinsir.hm.model.SysRole;
import com.jacklinsir.hm.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping("/list")
    public CommonResults list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        List<SysRole> all = roleService.roleList(page, limit);
        //调用分页工具进行统一分页
        CommonPage<SysRole> sysRoleCommonPage = CommonPage.restPage(all);
        return CommonResults.success(sysRoleCommonPage.getTotal().intValue(), sysRoleCommonPage.getList());

    }

    @ResponseBody
    @PostMapping("/findRoleByFuzzyRoleName")
    public CommonResults findRoleByFuzzyRoleName(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                 @RequestParam("roleName") String roleName) {
        try {
            List<SysRole> roleList = roleService.findRoleByFuzzyRoleName(page, limit, roleName);
            //调用分页工具进行统一分页
            CommonPage<SysRole> sysRoleCommonPage = CommonPage.restPage(roleList);
            return CommonResults.success(sysRoleCommonPage.getTotal().intValue(), sysRoleCommonPage.getList());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("角色高级查询-异常:{}", e.fillInStackTrace());
            return CommonResults.failure(ResponseCode.PARAMETER_MISSING.getCode(), e.getMessage());
        }
    }


    @ResponseBody
    @PostMapping("/edit")
    public CommonResults edit(@RequestBody RoleDto dto) {
        try {
            int update = roleService.updateRole(dto);
            if (update > 0) {
                return CommonResults.success();
            }
        } catch (Exception e) {
            log.info("用户删除发生异常了: {}", e.getMessage());
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }
        return CommonResults.failure();
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Integer id, ModelMap modelMap) {
        SysRole role = roleService.getByRoleId(id);
        modelMap.addAttribute("sysRole", role);
        return "role/role-edit";
    }

    @ResponseBody
    @GetMapping("/delete")
    public CommonResults delById(@RequestParam("id") Integer id) {
        try {
            int del = roleService.delById(id);
            if (del > 0) {
                return CommonResults.success();
            }
        } catch (Exception e) {
            log.info("用户删除发生异常了: {}", e.getMessage());
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }
        return CommonResults.failure();
    }

    @ResponseBody
    @PostMapping("/add")
    public CommonResults add(@RequestBody RoleDto dto) {
        log.info("角色添加请求参数: {}", dto);
        try {
            int index = roleService.save(dto);
            return index == 1 ? CommonResults.success(ResponseCode.SUCCESS) : CommonResults.success(ResponseCode.FAIL);
        } catch (Exception e) {
            log.info("角色添加出现异常: {}", e.getMessage());
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("sysRole", new SysRole());
        return "role/role-add";
    }

}
