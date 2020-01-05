package com.jacklinsir.hm.service;

import com.jacklinsir.hm.model.SysRole;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/2 21:12
 */
public interface SysRoleService {
    /**
     * 查询所有角色
     *
     * @return
     */
    List<SysRole> roleAll();
}
