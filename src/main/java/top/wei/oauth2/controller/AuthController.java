package top.wei.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.model.vo.CurrentUserVo;
import top.wei.oauth2.model.vo.UserInfoVo;
import top.wei.oauth2.service.UserService;

import java.util.List;

/**
 * AuthController.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * 获取当前用户信息.
     *
     * @param authentication the authentication
     * @return ResponseEntity with user information or unauthorized status
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = authentication.getName();
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(new CurrentUserVo(username, authorities));
    }

    /**
     * 获取当前用户信息.
     *
     * @param authentication the authentication
     * @return ResponseEntity with user information or unauthorized status
     */
    @PostMapping("/userinfo")
    public ResponseEntity<UserInfoVo> userinfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserInfoVo userInfo = userService.getUserInfo(authentication.getName());
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        userInfo.setAuthorities(authorities);
        return ResponseEntity.ok(userInfo);
    }

}