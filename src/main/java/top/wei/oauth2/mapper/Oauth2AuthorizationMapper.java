package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.wei.oauth2.model.entity.Oauth2Authorization;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 魏亮宁
 * @since 2023-06-13 14:30:59
 */
public interface Oauth2AuthorizationMapper extends BaseMapper<Oauth2Authorization> {

    /**
     * 通过 tokenIndexSha256 查找 认证信息
     *
     * @param tokenIndexSha256 token sha256 hash索引
     * @return List<Oauth2Authorization>
     */
    List<Oauth2Authorization> findByTokenType(@Param("tokenIndexSha256") String tokenIndexSha256);


}
