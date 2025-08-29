package top.wei.oauth2.security.login.captcha.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.PermissionMapper;
import top.wei.oauth2.security.login.captcha.service.CaptchaUserDetailsService;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * CaptchaUserDetailsService.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CaptchaUserDetailsServiceImpl implements CaptchaUserDetailsService {
    private final UserMapper userMapper;

    private final PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        UserLoginDto userLoginDto = userMapper.selectUserRoleByPhoneNumber(phone);
        if (Objects.isNull(userLoginDto)) {
            throw new UsernameNotFoundException(phone);
        }
        List<SimpleGrantedAuthority> authorities = buildAuthorities(userLoginDto);

        return User.withUsername(userLoginDto.getUsername())
                .password(userLoginDto.getPassword())
                .accountExpired(userLoginDto.getAccountExpired())
                .accountLocked(userLoginDto.getAccountLocked())
                .credentialsExpired(userLoginDto.getCredentialsExpired())
                .disabled(userLoginDto.getDisabled())
                .authorities(authorities)
                .build();
    }

    /**
     * buildAuthorities.
     *
     * @param userLoginDto userLoginDto
     * @return list of SimpleGrantedAuthority
     */
    private List<SimpleGrantedAuthority> buildAuthorities(UserLoginDto userLoginDto) {
        // 处理角色，确保有 ROLE_ 前缀
        List<String> roles = userLoginDto.getRoleNames().stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .toList();

        // 获取权限
        List<String> permissions = permissionMapper
                .selectPermissionByUserid(userLoginDto.getUserId())
                .stream()
                .map(PermissionDto::getPermissionCode)
                .toList();

        // 合并角色和权限，转为 SimpleGrantedAuthority
        return Stream.concat(roles.stream(), permissions.stream())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
