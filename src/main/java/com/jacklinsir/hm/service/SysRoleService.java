package com.jacklinsir.hm.service;

import com.jacklinsir.hm.dto.RoleDto;
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

    List<SysRole> roleList(Integer page, Integer limit);

    List<SysRole> findRoleByFuzzyRoleName(Integer page, Integer limit, String roleName);

    int save(RoleDto dto);

    int delById(Integer id);

    SysRole getByRoleId(Integer id);

    int updateRole(RoleDto dto);
}
