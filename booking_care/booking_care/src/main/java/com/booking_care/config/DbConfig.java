package com.booking_care.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DbConfig {

    @Bean("dsConfig")
    @ConditionalOnProperty(prefix = "spring", name = "datasource.jdbc-url")
    @ConfigurationProperties("spring.datasource")
    public HikariConfig primaryConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    public HikariDataSource primaryDataSource(@Qualifier("dsConfig") HikariConfig dsConfig) {
        // Không cần decrypt vì bạn không mã hóa mật khẩu
        return new HikariDataSource(dsConfig);
    }
}
