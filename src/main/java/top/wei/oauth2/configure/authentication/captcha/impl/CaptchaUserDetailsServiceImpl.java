package top.wei.oauth2.configure.authentication.captcha.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wei.oauth2.configure.authentication.captcha.CaptchaUserDetailsService;
import top.wei.oauth2.mapper.RoleMapper;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;

import java.util.List;
import java.util.Objects;

/**
 * CaptchaUserDetailsService.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CaptchaUserDetailsServiceImpl implements CaptchaUserDetailsService {
    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        UserLoginDto userLoginDto = userMapper.selectUserRoleByPhoneNumber(phone);
        if (Objects.isNull(userLoginDto)) {
            throw new UsernameNotFoundException(phone);
        }
        List<String> roleNames = userLoginDto.getRoleNames();
        String[] roles = roleNames.toArray(new String[0]);

        List<PermissionDto> permissionDtoS = roleMapper.selectPermissionByRoleNames(roleNames);
        String[] authorities = permissionDtoS.stream().map(PermissionDto::getPermissionCode).toArray(String[]::new);

        return User.withUsername(userLoginDto.getUsername())
                .password(userLoginDto.getPassword())
                .accountExpired(userLoginDto.getAccountExpired())
                .accountLocked(userLoginDto.getAccountLocked())
                .credentialsExpired(userLoginDto.getCredentialsExpired())
                .disabled(userLoginDto.getDisabled())
                .roles(roles)
                .authorities(authorities)
                .build();
    }
}
