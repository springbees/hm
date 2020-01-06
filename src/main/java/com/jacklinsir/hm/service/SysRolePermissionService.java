package com.jacklinsir.hm.service;

import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 20:27
 */
public interface SysRolePermissionService {

    int savePermission(Integer id, List<Long> permissionIds);


    int deleteRolePermission(Integer roleId);
}
