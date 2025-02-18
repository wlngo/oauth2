package top.wei.oauth2.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 魏亮宁
 * @date 2023年06月23日 20:37:00
 */
@Data
@Accessors(chain = true)
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}
