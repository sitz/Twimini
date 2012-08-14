package com.directi.train.tweetapp;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

@Configuration
public class AppConfig {

    @Bean
    public SimpleJdbcTemplate simpleJdbcTemplate1() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:postgresql://localhost/minitwitter");
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("qwerty");
        return new SimpleJdbcTemplate(basicDataSource);
    }

    @Bean
    public SimpleJdbcTemplate simpleJdbcTemplate2() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:postgresql://localhost/minitwitter");
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("qwerty");
        return new SimpleJdbcTemplate(basicDataSource);
    }

    @Bean
    public SimpleJdbcTemplate shardTemplate() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:postgresql://localhost/minitwitter");
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("qwerty");
        return new SimpleJdbcTemplate(basicDataSource);
    }
}
