package top.wei.oauth2.configure.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;


@Configuration
@EnableRedisIndexedHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24)
public class RedisSessionConfig {

}
