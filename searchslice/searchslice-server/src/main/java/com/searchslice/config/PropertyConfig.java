package com.searchslice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * To make spring evaluate ${...} placeholders within bean definition
 * property values (and @Value annotations against the current Spring
 * Environment and its set of PropertySources) we need to register
 * PropertySourcesPlaceholderConfigurer bean.
 */
@Configuration
public class PropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}