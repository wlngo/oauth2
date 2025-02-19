package top.wei.oauth2.configure.authentication;


import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import top.wei.oauth2.configure.authentication.captcha.CaptchaLoginFilterConfigurer;


/**
 * The type Login filter security configurer.
 *
 * @param <H> the type parameter
 */
public class LoginFilterSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, H> {
    private CaptchaLoginFilterConfigurer<H> captchaLoginFilterConfigurer;


    /**
     * Captcha login captcha login filter configurer.
     *
     * @return the captcha login filter configurer
     */
    public CaptchaLoginFilterConfigurer<H> captchaLogin() {
        return lazyInitCaptchaLoginFilterConfigurer();
    }

    /**
     * Captcha login login filter security configurer.
     *
     * @param captchaLoginFilterConfigurerCustomizer the captcha login filter configurer customizer
     * @return the login filter security configurer
     */
    public LoginFilterSecurityConfigurer<H> captchaLogin(Customizer<CaptchaLoginFilterConfigurer<H>> captchaLoginFilterConfigurerCustomizer) {
        captchaLoginFilterConfigurerCustomizer.customize(lazyInitCaptchaLoginFilterConfigurer());
        return this;
    }


    @Override
    public void init(H builder) throws Exception {
        if (captchaLoginFilterConfigurer != null) {
            captchaLoginFilterConfigurer.init(builder);
        }

    }

    @Override
    public void configure(H builder) throws Exception {
        if (captchaLoginFilterConfigurer != null) {
            captchaLoginFilterConfigurer.configure(builder);
        }

    }

    private CaptchaLoginFilterConfigurer<H> lazyInitCaptchaLoginFilterConfigurer() {
        if (captchaLoginFilterConfigurer == null) {
            this.captchaLoginFilterConfigurer = new CaptchaLoginFilterConfigurer<>(this);
        }
        return captchaLoginFilterConfigurer;
    }

}
