package com.jacklinsir.hm.service.impl;

import com.jacklinsir.hm.dao.SysRoleDao;
import com.jacklinsir.hm.model.SysRole;
import com.jacklinsir.hm.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (角色业务实现类)
 * @Date 2020/1/2 21:15
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao roleDao;
    @Override
    public List<SysRole> roleAll() {
        return roleDao.roleAll();
    }
}
