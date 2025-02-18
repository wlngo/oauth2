package top.wei.oauth2.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 魏亮宁
 * @date 2023年06月13日 14:57:00
 * mybatis 配置
 */
@MapperScan("top.wei.oauth2.mapper")
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

}
