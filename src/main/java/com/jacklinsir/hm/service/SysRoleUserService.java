package com.jacklinsir.hm.service;

import com.jacklinsir.hm.model.SysRoleUser;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/4 16:10
 */
public interface SysRoleUserService {
    /**
     * 根据用户ID查询用户所拥有的角色对象
     *
     * @param userId
     * @return
     */
    SysRoleUser getRoleUserByUserId(Integer userId);

    /**
     * 根据角色ID查询用户
     *
     * @param roleId
     * @return
     */
    List<SysRoleUser> listAllSysRoleUserByRoleId(Integer roleId);
}
