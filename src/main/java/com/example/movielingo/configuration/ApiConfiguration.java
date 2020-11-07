package com.example.movielingo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class ApiConfiguration{


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(MyConstants.DATABASE_URL);
        dataSourceBuilder.username(MyConstants.DATABASE_USERNAME);
        dataSourceBuilder.password(MyConstants.DATABASE_PASSWORD);
        return dataSourceBuilder.build();
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBean(){
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.addUrlPatterns(("/api/user/*"));
        filterRegistrationBean.setFilter(new JwtFilter());
        return filterRegistrationBean;
    }

}
