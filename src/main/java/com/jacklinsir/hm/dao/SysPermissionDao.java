package com.jacklinsir.hm.dao;

import com.jacklinsir.hm.model.SysPermission;
import com.jacklinsir.hm.model.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 16:30
 */
public interface SysPermissionDao {

    @Select("select *from sys_permission t")
    List<SysPermission> findAll();


    @Select("select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
    List<SysPermission> listAllPermissionByRoleId(Integer roleId);
}
