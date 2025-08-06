package top.wei.oauth2.model.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

/**
 * The Custom implementation of an {@link OAuth2User}.
 * User attribute names are <b>not</b> standardized between providers and therefore it is
 * required to supply the <i>key</i> for the user's &quot;name&quot; attribute to one of
 * the constructors. The <i>key</i> will be used for accessing the &quot;name&quot; of the
 * {@code Principal} (user) via {@link #getAttributes()} and returning it from
 * {@link #getName()}.
 *
 * @see CustomOAuth2UserDto
 */
@JsonSerialize
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class CustomOAuth2UserDto implements OAuth2User, Serializable {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Set<GrantedAuthority> authorities;

    private final Map<String, Object> attributes;

    private final String userName;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities the authorities granted to the user
     * @param attributes  the attributes about the user
     * @param name        the key for the user's &quot;name&quot; attribute
     *                    {@link #getAttributes()}
     */
    public CustomOAuth2UserDto(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
                               String name) {
        Assert.notEmpty(attributes, "attributes cannot be empty");
        Assert.hasText(name, "name cannot be empty");
        this.authorities = (authorities != null)
                ? Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)))
                : Collections.unmodifiableSet(new LinkedHashSet<>(AuthorityUtils.NO_AUTHORITIES));
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        this.userName = name;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    private Set<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
                Comparator.comparing(GrantedAuthority::getAuthority));
        sortedAuthorities.addAll(authorities);
        return sortedAuthorities;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        CustomOAuth2UserDto that = (CustomOAuth2UserDto) obj;
        if (!this.getName().equals(that.getName())) {
            return false;
        }
        if (!this.getAuthorities().equals(that.getAuthorities())) {
            return false;
        }
        return this.getAttributes().equals(that.getAttributes());
    }

    @Override
    public int hashCode() {
        int result = this.getName().hashCode();
        result = 31 * result + this.getAuthorities().hashCode();
        result = 31 * result + this.getAttributes().hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: [");
        sb.append(this.getName());
        sb.append("], Granted Authorities: [");
        sb.append(getAuthorities());
        sb.append("], User Attributes: [");
        sb.append(getAttributes());
        sb.append("]");
        return sb.toString();
    }

}
