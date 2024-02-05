package com.company.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "demoDbDataSource")
    @ConfigurationProperties("app.datasource.demo-db")
    public DataSource demoDbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbc_template")
    JdbcTemplate jdbcTemplate(@Qualifier("demoDbDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
