package top.wei.oauth2.configure.authentication.captcha.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.wei.oauth2.configure.authentication.captcha.CaptchaService;
import top.wei.oauth2.mapper.UserMapper;
import top.wei.oauth2.model.entity.User;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * CaptchaServiceImpl.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private static final String REDIS_PREFIX = "SMS-CODE%s";

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final UserMapper userMapper;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean verifyCaptcha(String phone, String rawCode) {
        String key = String.format(REDIS_PREFIX, phone);
        String verifyRawCode = redisTemplate.opsForValue().get(key);
        if (rawCode.equals(verifyRawCode)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }


    @Override
    public void sendSms(String phone) {
        boolean existsPhoneNumber = userMapper.exists(new QueryWrapper<User>()
                .select("phone_number")
                .eq("phone_number", phone));
        if (!existsPhoneNumber) {
            throw new UsernameNotFoundException(phone + "手机号不存在!");
        }
        String key = String.format(REDIS_PREFIX, phone);
        StringBuilder rawCode = new StringBuilder();
        int codeLength = 6;
        for (int i = 0; i < codeLength; i++) {
            int digit = RANDOM.nextInt(10);
            rawCode.append(digit);
        }
        redisTemplate.opsForValue().set(key, rawCode.toString(), 60 + 20, TimeUnit.SECONDS);
        log.info("phone {} code {}", phone, rawCode);
    }
}
