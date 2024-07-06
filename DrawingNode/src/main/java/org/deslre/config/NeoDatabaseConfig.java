package org.deslre.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class NeoDatabaseConfig {

    @Value("${spring.neo4j.uri}")
    private String url;

    @Value("${spring.neo4j.authentication.username}")
    private String userName;

    @Value("${spring.neo4j.authentication.password}")
    private String passWord;

}
