package com.jacklinsir.hm.dao;

import com.jacklinsir.hm.model.SysRole;
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
}
