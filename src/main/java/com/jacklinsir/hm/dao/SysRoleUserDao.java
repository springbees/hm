package com.jacklinsir.hm.dao;

import com.jacklinsir.hm.model.SysRoleUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/3 15:37
 */
public interface SysRoleUserDao {


    @Insert("insert into sys_role_user(userId,roleId)values (#{userId} ,#{roleId})")
    int save(SysRoleUser roleUser);

    @Select("select userId,roleId from sys_role_user where userId=#{userId}  ")
    SysRoleUser getRoleUserByUserId(Integer userId);

    @Select("select * from sys_role_user t where t.userId = #{userId}")
    SysRoleUser getSysRoleUserByUserId(int intValue);

    int updateSysRoleUser(SysRoleUser sysRoleUser);

    @Delete("delete from sys_role_user t where t.userId=#{id} ")
    int delRoleUser(Integer id);
}
