package com.jacklinsir.hm.service;

import com.alibaba.fastjson.JSONArray;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.model.SysPermission;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 16:28
 */
public interface SysPermissionService {

    /**
     * 查询所有权限
     *
     * @return
     */
    CommonResults<JSONArray> listAllPermission();

    /**
     * 根据角色ID查询当前角色拥有的权限
     *
     * @param id
     * @return
     */
    CommonResults<SysPermission> listAllPermissionByRoleId(Integer id);

    /**
     * 查询所有菜单权限
     *
     * @return
     */
    List<SysPermission> menuAll();

    /**
     * 保存权限
     *
     * @param permission
     * @return
     */
    int save(SysPermission permission);

    /**
     * 根据ID查询权限对象
     *
     * @param id
     * @return
     */
    SysPermission getSysPermissionById(Integer id);

    /**
     * 更新权限
     *
     * @param permission
     * @return
     */
    int updatePermission(SysPermission permission);


    /**
     * 根据ID删除权限
     *
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 查询菜单项
     *
     * @param userId
     * @return
     */
    CommonResults<SysPermission> getMenu(Integer userId);
}
