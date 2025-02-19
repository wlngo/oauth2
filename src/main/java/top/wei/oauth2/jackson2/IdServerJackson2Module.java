package top.wei.oauth2.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import top.wei.oauth2.configure.authentication.captcha.CaptchaAuthenticationToken;

public class IdServerJackson2Module extends SimpleModule {

    public IdServerJackson2Module() {
        super(IdServerJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(CaptchaAuthenticationToken.class, CaptchaAuthenticationTokenMixin.class);
    }
}
