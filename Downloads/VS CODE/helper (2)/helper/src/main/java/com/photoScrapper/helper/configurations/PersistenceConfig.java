package com.photoScrapper.helper.configurations;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
// ADDED: This annotation tells Spring to find, bind, and register
// OcrProperties as a bean, fixing the NullPointerException.
@EnableConfigurationProperties(OcrProperties.class)
public class PersistenceConfig {
    
}