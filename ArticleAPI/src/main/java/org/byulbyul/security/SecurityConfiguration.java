package org.byulbyul.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/articles*").hasRole("USER");
                    authConfig.requestMatchers(HttpMethod.POST, "/api/v1/articles*").hasRole("USER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/api/v1/articles*").hasRole("USER");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/api/v1/articles*").hasRole("USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/api/v1/statistics").hasRole("ADMIN");
                    authConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults()) // Login with browser and Build in Form
                .httpBasic(withDefaults()); // Login with Insomnia or Postman and Basic Auth
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        var admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("USER", "ADMIN")
                .build();
        var user = User.builder()
                .username("byulbyul")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}