package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.wei.oauth2.model.entity.Oauth2Scope;

import java.util.Collection;
import java.util.List;

public interface Oauth2ScopeMapper extends BaseMapper<Oauth2Scope> {

    /**
     * 根据客户端ID和范围查询.
     *
     * @param registeredClientId registeredClientId
     * @param scopes              scopes
     * @return Oauth2Scope.
     */
    List<Oauth2Scope> findByClientIdAndScope(String registeredClientId, Collection<String> scopes);
}
