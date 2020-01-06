package com.jacklinsir.hm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
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
import java.util.stream.Collectors;

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
    public SysPermission getSysPermissionById(Integer id) {
        if (id != null && id > 0) {
            return permissionDao.getSysPermissionById(id);
        }
        return null;

    }

    @Override
    public CommonResults getMenu(Integer userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        //筛出父节点也就是菜单不是按钮
        datas = datas.stream().filter(permission -> permission.getType().equals(1)).collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray();
        log.info(getClass().getName() + ".setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0, datas, jsonArray);
        return CommonResults.success(jsonArray);
    }

    @Override
    public int deleteById(Integer id) {
        if (id != null && id > 0) {
            //先删除子节点
            int deleteById = permissionDao.deleteById(id);
            log.info("删除子节点数量: {}", deleteById);
            //在删除父节点
            int deleteByParentId = permissionDao.deleteByParentId(id);
            log.info("删除父节点数量: {}", deleteByParentId);
            return 1;
        }
        return 0;
    }

    @Override
    public int updatePermission(SysPermission permission) {
        if (ObjectUtil.isNull(permission)) {
            throw new RuntimeException("参数异常");
        }
        return permissionDao.updatePermission(permission);
    }

    @Override
    public int save(SysPermission permission) {
        log.info("service-保存权限资源参数: {}", permission);
        if (ObjectUtil.isNull(permission)) {
            throw new RuntimeException("参数异常");
        }
        return permissionDao.save(permission) == 1 ? 1 : 0;
    }

    @Override
    public List<SysPermission> menuAll() {
        List<SysPermission> all = permissionDao.findAll();
        log.info("菜单数据列表：{}", all);
        return all;
    }

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
