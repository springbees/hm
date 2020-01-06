package com.jacklinsir.hm.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jacklinsir.hm.model.SysPermission;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (底边遍历树节点)
 * @Date 2020/1/5 16:33
 */
@Slf4j
public class TreeUtils {

    /**
     * 菜单树遍历实现方法
     *
     * @param parentId       父ID
     * @param permissionsAll 所有权限
     * @param array          Json数组对象
     */
    public static void setPermissionsTree(Integer parentId, List<SysPermission> permissionsAll, JSONArray array) {
        //遍历所有权限
        for (SysPermission pms : permissionsAll) {
            //判断是否还有相同父节点
            if (parentId.equals(pms.getParentId())) {
                String string = JSONObject.toJSONString(pms);
                //在将上面字符串序列化成JSON对象
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                //添加进JSON数组
                array.add(parent);
                //拿到父节点ID和权限ID进行比较拿到子节点
                if (permissionsAll.stream().filter(sysPermission -> sysPermission.getParentId().equals(pms.getId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(pms.getId(), permissionsAll, child);
                }
            }
        }
    }
}
