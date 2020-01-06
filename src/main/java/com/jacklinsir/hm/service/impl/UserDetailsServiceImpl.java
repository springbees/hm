package com.jacklinsir.hm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jacklinsir.hm.dao.SysPermissionDao;
import com.jacklinsir.hm.dto.LoginUser;
import com.jacklinsir.hm.model.SysPermission;
import com.jacklinsir.hm.model.SysUser;
import com.jacklinsir.hm.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (处理用户登入service)
 * @Date 2020/1/6 19:33
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userService.getUser(s);
        if (ObjectUtil.isNull(user)) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在！");
        } else if (user.getStatus() == SysUser.Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (user.getStatus() == SysUser.Status.DISABLED) {
            throw new DisabledException("用户已经注销");
        }
        LoginUser loginUser = new LoginUser();
        //将当前对象赋值到目标对象
        BeanUtils.copyProperties(user, loginUser);
        //查询当前用户的权限
        List<SysPermission> sysPermissions = permissionDao.listByUserId(user.getId());
        loginUser.setPermissions(sysPermissions);
        return loginUser;
    }
}
