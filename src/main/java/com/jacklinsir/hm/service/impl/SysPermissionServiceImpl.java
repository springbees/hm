package com.jacklinsir.hm.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.jacklinsir.hm.common.result.CommonResults;
import com.jacklinsir.hm.common.result.ResponseCode;
import com.jacklinsir.hm.common.utils.TreeUtils;
import com.jacklinsir.hm.dao.SysPermissionDao;
import com.jacklinsir.hm.model.SysPermission;
import com.jacklinsir.hm.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 16:28
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao permissionDao;

    @Override
    public CommonResults listAllPermissionByRoleId(Integer id) {
        log.info("根据角色ID查询权限: {}", id);
        if (id != null && id > 0) {
            return CommonResults.success(ResponseCode.SUCCESS, permissionDao.listAllPermissionByRoleId(id));
        }
        return CommonResults.failure();
    }

    @Override
    public CommonResults<JSONArray> listAllPermission() {
        log.info(getClass().getName() + ".listAllPermission()");
        List<SysPermission> datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        log.info(getClass().getName() + ".setPermissionsTree(?,?,?)");
        //调用递归工具类 root节点为0
        TreeUtils.setPermissionsTree(0, datas, array);
        return CommonResults.success(array);
    }
}
