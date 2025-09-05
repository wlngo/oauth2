package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.wei.oauth2.model.entity.Permission;
import top.wei.oauth2.model.entity.RolePermissionRelation;

import java.util.List;


public interface RolePermissionRelationMapper extends BaseMapper<RolePermissionRelation> {

    /**
     * 根据角色ID获取权限列表.
     * @param roleId roleId
     * @return Permission
     */
    List<Permission> queryPermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色ID获取未拥有的权限列表.
     * @param roleId roleId
     * @return Permission
     */
    List<Permission> queryPermissionNotAssignedToRoleId(@Param("roleId") String roleId);

}
