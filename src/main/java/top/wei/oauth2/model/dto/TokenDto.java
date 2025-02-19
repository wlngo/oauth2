package top.wei.oauth2.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Accessors(chain = true)
public class TokenDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * token 值.
     */
    private String tokenValue;


    /**
     * token 发出时间.
     */
    private Timestamp tokenIssuedAt;


    /**
     * token 过期时间.
     */
    private Timestamp tokenExpiresAt;

    /**
     * token元信息.
     */
    private String metadata;
}
