package com.onlinemobilestore.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ResourseConfig {
    @Bean
    public MessageSource getMessagSource(){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:/messages/user");
        ms.setDefaultEncoding("utf-8");
        return ms;
    }
}
