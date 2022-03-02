package com.j0schi.server.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Data
@Configuration
public class JDBCConfig {

    private final ConfigVariables configVariables;

    @Bean
    public DataSource sqlDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(configVariables.getDbDriver());
        dataSource.setUrl(configVariables.getDbUrl());
        dataSource.setUsername(configVariables.getDbUser());
        dataSource.setPassword(configVariables.getDbPassword());
        return dataSource;
    }


}
