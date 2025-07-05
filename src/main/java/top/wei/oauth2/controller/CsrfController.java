package top.wei.oauth2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CsrfController.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CsrfController {

    /**
     * return X-CSRF-TOKEN.
     * @param token token
     * @return csrf token
     */
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
