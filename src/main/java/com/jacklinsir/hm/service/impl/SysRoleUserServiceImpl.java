package com.jacklinsir.hm.service.impl;

import com.jacklinsir.hm.dao.SysRoleUserDao;
import com.jacklinsir.hm.model.SysRoleUser;
import com.jacklinsir.hm.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/4 16:12
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {

    @Autowired
    private SysRoleUserDao roleUserDao;

    @Override
    public SysRoleUser getRoleUserByUserId(Integer userId) {
        if (userId <= 0) {
            throw new RuntimeException("参数异常");
        }
        return roleUserDao.getRoleUserByUserId(userId);
    }
}
