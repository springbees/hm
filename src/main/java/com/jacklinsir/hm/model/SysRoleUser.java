package com.jacklinsir.hm.model;

import lombok.Data;

/**
 * 用户和角色关联边表
 *
 * @author linSir
 */
@Data
public class SysRoleUser {
    private Integer userId;
    private Integer roleId;
}
