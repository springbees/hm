package com.jacklinsir.hm.dao;

import com.jacklinsir.hm.model.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用户dao接口)
 * @Date 2019/12/31 13:54
 */
public interface SysUserDao {

    @Select("select * from sys_user t where t.username = #{username}")
    SysUser getUser(String username);

    @Select("select * from sys_user t")
    List<SysUser> queryAll();

    @Delete("delete from sys_user where id=#{id} ")
    Integer delById(Integer id);

    @Select("select * from sys_user where telephone=#{telephone} ")
    SysUser getUserByPhone(String telephone);

    @Select("select * from sys_user where email=#{email} ")
    SysUser getUserByEmail(String email);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now())")
    int save(SysUser user);

    @Select("select *from sys_user where id=#{id} ")
    SysUser getUserById(Integer id);

    int upadte(SysUser user);

    List<SysUser> findUserByFuzzyUserName(@Param("username") String username);

}
