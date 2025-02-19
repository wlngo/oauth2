package top.wei.oauth2.model.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Data
public class OAuth2Scope implements Serializable {
    @Serial
    private static final long serialVersionUID = 6603836530809864931L;

    private String clientId;

    private String scope;

    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OAuth2Scope that = (OAuth2Scope) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(scope, that.scope) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, scope, description);
    }
}
