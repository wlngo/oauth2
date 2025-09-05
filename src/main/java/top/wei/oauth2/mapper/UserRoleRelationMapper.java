package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.wei.oauth2.model.entity.Role;
import top.wei.oauth2.model.entity.UserRoleRelation;

import java.util.List;

public interface UserRoleRelationMapper extends BaseMapper<UserRoleRelation> {

    /**
     * 根据用户ID查询角色列表.
     *
     * @param userId userId
     * @return Role
     */
    List<Role> queryRolesByUserId(@Param("userId") String userId);


    /**
     * 查询未分配给指定用户的角色列表.
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> queryRolesNotAssignedToUser(@Param("userId") String userId);

}
