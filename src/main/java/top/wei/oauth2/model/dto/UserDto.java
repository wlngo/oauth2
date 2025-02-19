package top.wei.oauth2.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}
