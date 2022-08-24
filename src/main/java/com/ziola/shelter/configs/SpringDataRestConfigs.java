package com.ziola.shelter.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class SpringDataRestConfigs {

  @Bean
  public RepositoryRestConfigurer repositoryRestConfigurer() {
    return new RepositoryRestConfigurer() {

      public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBasePath("/apprest");
      }
    };
  }
}
