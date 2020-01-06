package com.jacklinsir.hm.dao;

import com.jacklinsir.hm.model.SysPermission;
import org.apache.ibatis.annotations.*;

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


    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_permission(parentId, name, css, href, type, permission, sort) values(#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort})")
    int save(SysPermission permission);

    @Select("select *from sys_permission where id=#{id} ")
    SysPermission getSysPermissionById(Integer id);

    int updatePermission(SysPermission permission);


    @Delete("delete from sys_permission where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from sys_permission where parentId = #{parentId}")
    int deleteByParentId(Integer parentId);


    @Select("SELECT DISTINCT sp.*  " +
            "FROM sys_role_user sru " +
            "INNER JOIN sys_role_permission srp ON srp.roleId = sru.roleId " +
            "LEFT JOIN sys_permission sp ON srp.permissionId = sp.id " +
            "WHERE " +
            "sru.userId = #{userId}")
    List<SysPermission> listByUserId(@Param("userId") Integer userId);
}
