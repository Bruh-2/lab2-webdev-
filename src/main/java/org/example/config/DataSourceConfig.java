package org.example.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("jdbcclient")
    @ConfigurationProperties("spring.datasource.ds2")
    public HikariDataSource ds2() {
        return new HikariDataSource();
    }
}
