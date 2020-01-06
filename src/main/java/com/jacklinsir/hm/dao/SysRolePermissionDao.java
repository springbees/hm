package com.jacklinsir.hm.dao;

        import org.apache.ibatis.annotations.Delete;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

/**
 * @author linSir
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @Date 2020/1/5 20:30
 */
public interface SysRolePermissionDao {

    /**
     * 保存角色和权限关联表
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    int saveRoleOrPermission(@Param("roleId") Integer roleId, @Param("permissionIds") List<Long> permissionIds);

    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    int deleteRolePermission(Integer roleId);
}
