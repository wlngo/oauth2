package top.wei.oauth2.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wei.oauth2.security.login.captcha.service.CaptchaService;
import top.wei.oauth2.utils.Rest;
import top.wei.oauth2.utils.RestBody;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @PostMapping("/captcha/sendSms")
    public Rest<?> bar(String phoneNumber, HttpServletRequest httpServletRequest) {
        log.info("{} httpServerRequest {} sendSms ", httpServletRequest.getRemoteAddr(), phoneNumber);
        try {
            captchaService.sendSms(phoneNumber);
        } catch (Exception e) {
            log.info("{} httpServerRequest {} sendSms fail {}", httpServletRequest.getRemoteAddr(), phoneNumber, e.getMessage());
            return RestBody.failure(401, e.getMessage());
        }
        return RestBody.okData(null, "成功发送!");
    }


}
