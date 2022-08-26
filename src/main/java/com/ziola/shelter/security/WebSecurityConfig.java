package com.ziola.shelter.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    private final DataSource dataSource;

    @Bean
    public UserDetailsManager authenticateUsers() {
        final String workersQuery = "select worker_email, worker_password, worker_active from worker where worker_email=?";
        final String rolesQuery = "select Worker.Worker_email, Role.worker_role from Worker inner join Role on Worker.role_id = role.role_id where worker.worker_email=?";

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setAuthoritiesByUsernameQuery(rolesQuery);
        users.setUsersByUsernameQuery(workersQuery);
        return users;
    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                        .authorizeRequests()
                        .antMatchers("/", "/login", "/rest", "/apprest/**", "/registrationConfirm", "/showAllAnimals/**", "/registration", "/static/**", "/images/**").permitAll()
                        .antMatchers("/**/*.js", "/**/*.css", "/**/*.jpg").permitAll()
                        .antMatchers("/admin/**").hasAuthority("Admin")
                        .anyRequest().authenticated()
                        .and()
                        .csrf()
                        .disable()
                        .formLogin()
                        .loginPage("/login").failureUrl("/login?error=true")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .and()
                        .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .and()
                        .exceptionHandling()
                        .accessDeniedPage("/access-denied")
                        .and()
                        .cors().and().csrf().disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/templates/**", "/seeDetails/**");
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
