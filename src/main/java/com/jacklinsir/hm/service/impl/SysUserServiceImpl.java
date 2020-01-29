package com.jacklinsir.hm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.common.utils.WebFileUtils;
import com.jacklinsir.hm.dao.SysRoleUserDao;
import com.jacklinsir.hm.dao.SysUserDao;
import com.jacklinsir.hm.model.SysRoleUser;
import com.jacklinsir.hm.model.SysUser;
import com.jacklinsir.hm.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用户service 事物遇到异常需要回滚)
 * @Date 2019/12/31 13:56
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {
    /**
     * 根据类型注入dao组件
     */
    @Autowired
    private SysUserDao userDao;

    @Autowired
    private SysRoleUserDao roleUserDao;

    @Override
    public SysUser getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public SysUser getByUserId(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public SysUser getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public int changePassword(String username, String oldPassword, String newPassword) {
        if (StrUtil.isNotBlank(username) && StrUtil.isNotBlank(oldPassword) && StrUtil.isNotBlank(newPassword)) {
            SysUser u = userDao.getUser(username);
            if (ObjectUtil.isNull(u)) {
                throw new RuntimeException("用户名不存在");
            }
            if (!new BCryptPasswordEncoder().encode(oldPassword).equals(u.getPassword())) {
                throw new RuntimeException("旧密码错误");
            }
            return userDao.changePassword(u.getId(), new BCryptPasswordEncoder().encode(newPassword));
        }
        return 0;
    }

    @Override
    public int edit(SysUser user, Integer roleId) {
        //校验字段
        filedVerify(user, roleId);
        //更新用户开始
        if (roleId != null && roleId != 0) {
            if (userDao.upadte(user) == 1) {
                //组装中间表关系
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setUserId(user.getId().intValue());
                sysRoleUser.setRoleId(roleId);
                //查用当前用户是否拥有该角色，在进行更新
                if (roleUserDao.getSysRoleUserByUserId(user.getId().intValue()) != null) {
                    return roleUserDao.updateSysRoleUser(sysRoleUser) == 1 ? 1 : 0;
                } else {
                    //当前用户不存在角色给他赋予角色
                    return roleUserDao.save(sysRoleUser) == 1 ? 1 : 0;
                }
            }
        }
        return 0;
    }

    /**
     * 校验字段
     *
     * @param user
     * @param roleId
     */
    private void filedVerify(SysUser user, Integer roleId) {
        SysUser sysUser = null;
        if (ObjectUtil.isNull(user) && roleId == 0) {
            throw new RuntimeException(ResponseCode.PARAMETER_MISSING.getMessage());
        }
        //校验用户名
        sysUser = userDao.getUser(user.getUsername());
        if (sysUser != null && !(sysUser.getId().equals(user.getId()))) {
            throw new RuntimeException(ResponseCode.USERNAME_REPEAT.getMessage());
        }
        //校验邮箱
        sysUser = userDao.getUserByPhone(user.getTelephone());
        if (sysUser != null && !(sysUser.getId().equals(user.getId()))) {
            throw new RuntimeException(ResponseCode.PHONE_REPEAT.getMessage());
        }

        //校验邮箱
        sysUser = userDao.getUserByEmail(user.getEmail());
        if (sysUser != null && !(sysUser.getId().equals(user.getId()))) {
            throw new RuntimeException(ResponseCode.EMAIL_REPEAT.getMessage());
        }
    }

    @Override
    public Integer save(SysUser user, Integer roleId) {
        //校验字段
        filedVerify(user, roleId);
        //设置用户默认状态
        user.setStatus(1);
        //设置密码加密
        //user.setPassword(MD5.crypt(user.getPassword()));
        //使用Spring Security提供的密码加密器
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        //保存用户开始
        if (roleId != null && roleId != 0) {
            int userSave = userDao.save(user);
            //构造SysRoleUser对象
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(user.getId());
            //保存数据库
            int roleSave = roleUserDao.save(roleUser);
            //校验两条返回数据是否相等
            return userSave == roleSave ? 1 : 0;
        }
        return 0;
    }

    @Override
    public Integer delById(Integer id) throws IOException {
        if (id > 0 && ObjectUtil.isNotNull(id)) {
            log.info("删除用户ID：{}", id);
            //删除用户之前把当前用户的角色关联表进行删除
            int delRoleUser = roleUserDao.delRoleUser(id);
            //根据ID查询出对象
            SysUser userById = userDao.getUserById(id);
            if (delRoleUser >= 0) {
                if (userDao.delById(id) > 0) {
                    //将头像文件进行删除，防止占用空间
                    WebFileUtils.disFile(userById.getHeadImgUrl());
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public List<SysUser> queryAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<SysUser> data = userDao.queryAll();
        log.info("用户列表数据：{}", data);
        return data;
    }

    @Override
    public List<SysUser> findUserByFuzzyUserName(Integer page, Integer limit, String username) {
        if (!StrUtil.isNotBlank(username)) {
            throw new RuntimeException("查询参数异常");
        }
        PageHelper.startPage(page, limit);
        List<SysUser> data = userDao.findUserByFuzzyUserName(username);
        log.info("用户列表数据：{}", data);
        return data;

    }
}
