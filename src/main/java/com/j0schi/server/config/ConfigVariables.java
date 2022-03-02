package com.j0schi.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "config")
public class ConfigVariables {
    private String dbDriver;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
}
