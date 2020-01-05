package com.jacklinsir.hm.model;

import lombok.Data;

/**
 * 角色和权限关联表
 * @author linSir
 */
@Data
public class RolePermission {
    private Integer roleId;
    private Integer permissionId;
}
