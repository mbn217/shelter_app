package com.ziola.shelter.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceConfig {

//  @Bean
//  public LocalValidatorFactoryBean getValidator() {
//    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//    bean.setValidationMessageSource(messageSource());
//    return bean;
//  }

  @Bean
  @Qualifier("customMessages")
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource resource =
        new ReloadableResourceBundleMessageSource();
    resource.setBasename("classpath:messages");
    resource.setDefaultEncoding("UTF-8");
    return resource;
  }
}
