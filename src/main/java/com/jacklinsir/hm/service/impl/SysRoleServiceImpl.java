package com.jacklinsir.hm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.jacklinsir.hm.dao.SysRoleDao;
import com.jacklinsir.hm.dao.SysRoleUserDao;
import com.jacklinsir.hm.dto.RoleDto;
import com.jacklinsir.hm.model.SysRole;
import com.jacklinsir.hm.model.SysRoleUser;
import com.jacklinsir.hm.service.SysRolePermissionService;
import com.jacklinsir.hm.service.SysRoleService;
import com.jacklinsir.hm.service.SysRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (角色业务实现类)
 * @Date 2020/1/2 21:15
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao roleDao;
    @Autowired
    private SysRoleUserService roleUserService;

    @Autowired
    private SysRolePermissionService rolePermissionService;

    @Override
    public int updateRole(RoleDto dto) {
        //校验参数并校验角色名
        if (ObjectUtil.isNull(dto)) {
            throw new RuntimeException("参数异常");
        }
        //获取权限集合
        List<Long> permissionIds = dto.getPermissionIds();
        //删除root节点
        permissionIds.remove(0);
        //更新角色权限之前要删除该角色之前的所有权限
        rolePermissionService.deleteRolePermission(dto.getId());
        //2、判断该角色是否有赋予权限值，有就添加"
        if (!CollectionUtils.isEmpty(permissionIds)) {
            //存在，则添加权限
            rolePermissionService.savePermission(dto.getId(), permissionIds);
        }
        //更新角色
        int count = roleDao.update(dto);
        return count == 1 ? 1 : 0;
    }

    @Override
    public SysRole getByRoleId(Integer id) {
        return roleDao.getByRoleId(id);
    }

    @Override
    public int delById(Integer roleId) {
        log.info("被删除的角色ID: {}", roleId);
        if (roleId != null && roleId != 0) {
            //首先查询出是否有用户使用该角色，有用户使用该角色不能进行删除
            List<SysRoleUser> roleUserList = roleUserService.listAllSysRoleUserByRoleId(roleId);
            //表示当前删除的角色没有关联用户
            if (roleUserList.size() <= 0) {
                //删除角色，因为数据库采用级联的方式关联权限表，所以角色删除，中间表会跟着一起删除
                try {
                    int del = roleDao.delById(roleId);
                    return del == 1 ? 1 : 0;
                } catch (Exception e) {
                    throw new RuntimeException("当前角色关联用户，不能被删除！");
                }
            }
            throw new RuntimeException("当前角色关联用户，不能被删除！");
        }
        return 0;
    }

    @Override
    public int save(RoleDto dto) {
        if (ObjectUtil.isNull(dto)) {
            throw new RuntimeException("参数异常");
        }
        SysRole roleByUsername = roleDao.getRoleByUsername(dto.getName());
        log.info("校验角色名结果: {}", roleByUsername);
        if (!ObjectUtil.isNull(roleByUsername)) {
            throw new RuntimeException("角色名已经存在了！");
        }
        //1. 保存角色
        int save = roleDao.saveRole(dto);
        //2. 获取当前角色所拥有的权限
        List<Long> permissionIds = dto.getPermissionIds();
        //移除0,permission id是从1开始
        permissionIds.remove(0L);
        //3. 保存角色对应的所有权限
        int count = 0;
        if (!permissionIds.isEmpty()) {
            int index = rolePermissionService.savePermission(dto.getId(), permissionIds);
            if (index == permissionIds.size()) {
                count = 1;
            }
            log.info("保存角色和权限影响行数: {}", count);
        }
        return save == count ? 1 : 0;
    }


    @Override
    public List<SysRole> findRoleByFuzzyRoleName(Integer page, Integer limit, String roleName) {
        PageHelper.startPage(page, limit);
        if (!StrUtil.isNotBlank(roleName)) {
            throw new RuntimeException("查询参数异常！");
        }
        List<SysRole> data = roleDao.findRoleByFuzzyRoleName(roleName);
        return data;
    }

    @Override
    public List<SysRole> roleList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<SysRole> data = roleDao.roleAll();
        log.info("角色结果集列表:{}", data);
        return data;
    }

    @Override
    public List<SysRole> roleAll() {
        return roleDao.roleAll();
    }

}
