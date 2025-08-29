package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.entity.Permission;

import java.util.List;


public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过用户ID查询权限.
     *
     * @param userId userId
     * @return PermissionDto
     */
    List<PermissionDto> selectPermissionByUserid(@Param("userid") String userId);

}
