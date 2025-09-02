package top.wei.oauth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.wei.oauth2.model.entity.Menu;

import java.util.List;

/**
 * MenuMapper.
 */
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * searchAllByUserId.
     *
     * @param userId userId
     * @return Menu
     */
    List<Menu> searchAllByUserId(String userId);

}