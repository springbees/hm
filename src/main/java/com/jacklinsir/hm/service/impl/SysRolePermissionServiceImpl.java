package com.jacklinsir.hm.service.impl;

import com.jacklinsir.hm.dao.SysRolePermissionDao;
import com.jacklinsir.hm.service.SysRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 20:28
 */
@Slf4j
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionDao rolePermissionDao;

    @Override
    public int savePermission(Integer id, List<Long> permissionIds) {
        if (id == 0 && id == null && permissionIds.isEmpty()) {
            throw new RuntimeException("参数异常");
        }
        return rolePermissionDao.saveRoleOrPermission(id, permissionIds);
    }

    @Override
    public int deleteRolePermission(Integer roleId) {
        return rolePermissionDao.deleteRolePermission(roleId);
    }
}
