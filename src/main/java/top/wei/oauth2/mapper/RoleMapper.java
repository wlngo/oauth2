package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.entity.Role;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 魏亮宁
 * @since 2023-06-13 14:30:59
 */
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 通过角色查询权限
     * @param roleNames 角色
     * @return PermissionDto>
     */
    List<PermissionDto> selectPermissionByRoleNames(List<String> roleNames);

}
