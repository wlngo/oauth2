package top.wei.oauth2.configure.authentication.captcha;

/**
 * @author n1
 */
public interface CaptchaService {

    /**
     * verify captcha
     *
     * @param phone   phone
     * @param rawCode rawCode
     * @return isVerified
     */
    boolean verifyCaptcha(String phone, String rawCode);


    /**
     * sendSms
     *
     * @param phone phone
     */
    void sendSms(String phone);
}
