package com.jacklinsir.hm.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jacklinsir.hm.common.result.CommonPage;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.common.utils.FileUtils;
import com.jacklinsir.hm.common.utils.WebFileUtils;
import com.jacklinsir.hm.dto.UserDto;
import com.jacklinsir.hm.model.SysUser;
import com.jacklinsir.hm.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.jacklinsir.hm.common.result.ResponseCode.FAIL;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用户Controller)
 * @Date 2019/12/31 14:04
 */
@Api(tags = "SysUserController", description = "用户管理")
@Slf4j
@Controller
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    /**
     * 根据用户名查询用户对象
     *
     * @param username
     * @return
     */
    @ApiOperation("根据用户名查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true)
    })
    @GetMapping("/get/{username}")
    @ResponseBody
    public Object getUsername(@PathVariable String username) {
        return userService.getUser(username);
    }

    @ApiOperation("查询用户列表")
    @PostMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    public CommonResults list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<SysUser> list = userService.queryAll(page, limit);
        //进行统一分页
        CommonPage commonPage = CommonPage.restPage(list);
        return CommonResults.success(commonPage.getTotal().intValue(), commonPage.getList());
    }

    @ApiOperation("用户删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true),
    })
    @GetMapping("/delById")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    public CommonResults delById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        try {
            Integer index = userService.delById(id);
            if (index > 0) {
                return CommonResults.success();
            }
        } catch (Exception e) {
            log.info("用户删除发生异常了: {}", e.fillInStackTrace());
            return CommonResults.failure();
        }
        return CommonResults.failure();
    }


    @ApiOperation("文件上传")
    @ResponseBody
    @PostMapping("/fileUpload")
    public CommonResults fileUpload(MultipartFile file) {
        String fileURL = FileUtils.webUploadFile(file);
        if (StrUtil.isNotBlank(fileURL)) {
            return CommonResults.success(ResponseCode.SUCCESS_IMAGE_FILE, fileURL);
        }
        return CommonResults.failure(ResponseCode.FAIL_IMAGE_FILE);

    }

    @ApiOperation("用户添加")
    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
    public CommonResults add(UserDto dto, @RequestParam(value = "roleId") Integer roleId) throws IOException {
        log.info("添加请求参数：{}", dto);
        try {
            return userService.save(dto, roleId) == 1 ? CommonResults.success(ResponseCode.SUCCESS) : CommonResults.failure(ResponseCode.FAIL);
        } catch (Exception e) {
            log.info("添加用户出现异常了: {}", e.fillInStackTrace());
            WebFileUtils.disFile(dto.getHeadImgUrl());
            //返回异常信息结果
            return CommonResults.failure(ResponseCode.FAIL.getCode(), e.getMessage());
        }
    }

    @ApiOperation("用户更新")
    @ResponseBody
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public CommonResults edit(UserDto dto, @RequestParam(value = "roleId") Integer roleId) throws IOException {
        log.info("用户请求参数:{}  角色ID: {}", dto, roleId);
        try {
            if (StrUtil.isNotBlank(dto.getHeadImgUrl())) {
                SysUser userById = userService.getUserById(dto.getId());
                //处理旧文件,查询如果旧文件存在者删除，不存在继续往下执行
                if (StrUtil.isNotBlank(userById.getHeadImgUrl())) {
                    WebFileUtils.disFile(dto.getHeadImgUrl());
                }
            }
            //修改用户
            return userService.edit(dto, roleId) == 1 ? CommonResults.success(ResponseCode.SUCCESS) : CommonResults.failure(ResponseCode.FAIL);
        } catch (Exception e) {
            //处理失败的文件进行上传
            WebFileUtils.disFile(dto.getHeadImgUrl());
            log.info("修改用户出现异常了: {}", e.fillInStackTrace());
            return CommonResults.failure(FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * @param username
     * @param page
     * @param limit
     * @return
     * @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")//描述
     * @ApiImplicitParams({
     * @ApiImplicitParam(name = "username",value = "模糊搜索的用户名", required = true),
     * })
     */
    @ResponseBody
    @PostMapping("/findUserByFuzzyUserName")
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "模糊搜索的用户名", required = true),
    })
    public CommonResults findUserByFuzzyUserName(@RequestParam("username") String username,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        try {
            List<SysUser> list = userService.findUserByFuzzyUserName(page, limit, username);
            //进行统一分页
            CommonPage commonPage = CommonPage.restPage(list);
            return CommonResults.success(commonPage.getTotal().intValue(), commonPage.getList());
        } catch (Exception e) {
            log.info("高级查询-发生异常: {}", e.fillInStackTrace());
            return CommonResults.failure(FAIL.getCode(), e.getMessage());
        }
    }


    /**
     * 编辑页面，顺便回显数据
     *
     * @param modelMap
     * @param user
     * @return
     */
    @ApiOperation("用户编辑页面")
    @GetMapping("/edit")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public String edit(ModelMap modelMap, SysUser user) {
        modelMap.addAttribute("sysUser", userService.getByUserId(user.getId()));

        return "admin/admin-edit";
    }


    @ApiOperation("根据用户性别查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", required = true),
    })
    @ResponseBody
    @RequestMapping("/getUserSex")
    public CommonResults getUserSex(@RequestParam("userId") Integer userId) {
        SysUser byUserId = userService.getByUserId(userId);
        if (ObjectUtil.isNotNull(byUserId)) {
            return CommonResults.success(ResponseCode.SUCCESS, byUserId);
        }
        return CommonResults.failure();
    }

    /**
     * 定义时间类型转换器
     * 第二种解决方法就是在model类上贴一个@DateTimeFormat
     *
     * @param binder
     * @param request
     */
    @InitBinder
    private void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 跳转添加页面
     * 加上一个空对象防止前台数据绑定异常
     *
     * @param modelMap
     * @return
     */
    @ApiOperation("用户添加页面")
    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public String addView(ModelMap modelMap) {
        modelMap.addAttribute("sysUser", new SysUser());
        return "admin/admin-add";
    }


    @ApiOperation("用户修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", required = true),
            @ApiImplicitParam(name = "oldPassword", required = true),
            @ApiImplicitParam(name = "newPassword", required = true),
    })
    @ResponseBody
    @PostMapping("/changePassword")
    public CommonResults changePassword(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "oldPassword") String oldPassword,
                                        @RequestParam(value = "newPassword") String newPassword) {
        try {
            int changePassword = userService.changePassword(username, oldPassword, newPassword);
            return changePassword == 1 ? CommonResults.success() : CommonResults.failure();
        } catch (Exception e) {
            log.info("修改密码出现异常: {}", e.getMessage());
            return CommonResults.failure(FAIL.getCode(), e.getMessage());
        }
    }

}
