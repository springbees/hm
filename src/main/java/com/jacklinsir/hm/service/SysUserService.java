package com.jacklinsir.hm.service;

import com.jacklinsir.hm.dto.UserDto;
import com.jacklinsir.hm.model.SysUser;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2019/12/31 13:55
 */
public interface SysUserService {
    /**
     * 根据用户名查询用户对象
     *
     * @param username
     * @return
     */
    SysUser getUser(String username);

    /**
     * 查询结果列表
     *
     * @param page
     * @param limit
     * @return
     */
    List<SysUser> queryAll(Integer page, Integer limit);

    /**
     * 高级查询
     * @param page
     * @param limit
     * @param username
     * @return
     */
    List<SysUser> findUserByFuzzyUserName (Integer page, Integer limit,String username);

    /**
     * 根据ID删除用户
     *
     * @param id
     * @return
     */
    Integer delById(Integer id);

    /**
     * 保存用户
     *
     * @param user
     * @param roleId
     * @return
     */
    Integer save(SysUser user, Integer roleId);

    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @return
     */
    SysUser getByUserId(Integer id);

    /**
     * 根据用户查询ID
     *
     * @param id
     * @return
     */
    SysUser getUserById(Integer id);

    int edit(SysUser dto, Integer roleId);
}
