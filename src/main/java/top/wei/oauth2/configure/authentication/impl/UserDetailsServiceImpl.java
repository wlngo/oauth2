package top.wei.oauth2.configure.authentication.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wei.oauth2.mapper.RoleMapper;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.dto.PermissionDto;
import top.wei.oauth2.model.dto.UserLoginDto;

import java.util.List;

/**
 * 载用户特定数据的核心界面.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;


    private final RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLoginDto userLoginDto = userMapper.selectUserRoleByUserName(username);
        if (userLoginDto == null) {
            throw new UsernameNotFoundException(username);
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
