package com.jacklinsir.hm.controller;

import com.jacklinsir.hm.common.result.CommonPage;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.dto.RoleDto;
import com.jacklinsir.hm.model.SysRole;
import com.jacklinsir.hm.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/2 21:11
 */
@Api(tags = "SysRoleController", description = "角色管理")
@Slf4j
@Controller
@RequestMapping("role")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;

    @ApiOperation("查询所有角色")
    @ResponseBody
    @PostMapping("/all")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public CommonResults list() {
        if (roleService.roleAll().isEmpty()) {
            return CommonResults.failure();
        }
        return CommonResults.success(0, roleService.roleAll());
    }

    @ApiOperation("查询所有角色列表分页")
    @ResponseBody
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:query')")
    public CommonResults list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        List<SysRole> all = roleService.roleList(page, limit);
        //调用分页工具进行统一分页
        CommonPage<SysRole> sysRoleCommonPage = CommonPage.restPage(all);
        return CommonResults.success(sysRoleCommonPage.getTotal().intValue(), sysRoleCommonPage.getList());

    }

    @ApiOperation("根据用户名模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "模糊搜索的角色名", required = true),
    })
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


    @ApiOperation("角色更新")
    @ResponseBody
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('sys:role:edit')")
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

    @ApiOperation("角色编辑页面")
    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public String edit(@RequestParam("id") Integer id, ModelMap modelMap) {
        SysRole role = roleService.getByRoleId(id);
        modelMap.addAttribute("sysRole", role);
        return "role/role-edit";
    }

    @ApiOperation("角色删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true)
    })
    @ResponseBody
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
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

    @ApiOperation("角色添加")
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
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
    @ApiOperation("角色添加页面")
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("sysRole", new SysRole());
        return "role/role-add";
    }

}
