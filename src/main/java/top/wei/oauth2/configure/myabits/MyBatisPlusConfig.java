package top.wei.oauth2.configure.myabits;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis 配置.
 */
@MapperScan("top.wei.oauth2.mapper")
@EnableTransactionManagement
@Configuration
public class MyBatisPlusConfig {

}
