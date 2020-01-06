package com.jacklinsir.hm.service;

import com.alibaba.fastjson.JSONArray;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.model.SysPermission;

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
}
