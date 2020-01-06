package com.jacklinsir.hm.dao;

import com.jacklinsir.hm.dto.RoleDto;
import com.jacklinsir.hm.model.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/2 21:16
 */
public interface SysRoleDao {

    /**
     * 查询所有角色数据
     *
     * @return
     */
    @Select("select *from sys_role")
    List<SysRole> roleAll();

    List<SysRole> findRoleByFuzzyRoleName(@Param("roleName") String roleName);

    @Select("select *from sys_role where name=#{name} ")
    SysRole getRoleByUsername(String name);

    int saveRole(RoleDto dto);

    @Delete("delete from sys_role where id=#{roleId} ")
    int delById(Integer roleId);

    @Select("select *from sys_role where id=#{id} ")
    SysRole getByRoleId(Integer id);

    int update(RoleDto dto);
}
