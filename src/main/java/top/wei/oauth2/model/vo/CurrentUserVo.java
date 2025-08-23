package top.wei.oauth2.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * CurrentUserVo.
 */

@Data
@AllArgsConstructor
public class CurrentUserVo {

    private String username;

    private List<String> authorities;

}