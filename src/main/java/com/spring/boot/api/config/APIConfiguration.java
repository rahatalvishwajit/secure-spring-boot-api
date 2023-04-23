package com.spring.boot.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to configure the API Application for basic authentication
 *
 * @author Vishwajit
 */

@Getter
@Setter
@Slf4j
@Configuration
@EnableWebSecurity
public class APIConfiguration {

    @Value("${authorize.username}")
    private String username;

    @Value("${authorize.password}")
    private char[] password;

    /**
     * This method defines a filter chain which is capable of being matched
     * against an HttpServletRequest
     *
     * @param httpSecurity HttpSecurity object
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeHttpRequests((auth) -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic();
        httpSecurity.cors().and().csrf().disable();
        return httpSecurity.build();
    }

    /**
     * This method provides support for username/password based
     * authentication that is stores in memory
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
                .withUsername(getUsername())
                .password(passwordEncoder().encode(String.valueOf(getPassword())))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * This method will return the instance of PasswordEncoder that
     * uses BCrypt strong hashing function
     *
     * @return PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method will be called when the spring boot application is ready
     * and replace the contents of password field with zeroes
     *
     * @param applicationReadyEvent Event to indicate that the application
     *                              is ready to service requests
     */
    @EventListener
    private void cleanPassword(ApplicationReadyEvent applicationReadyEvent) {

        log.debug("Entering method cleanPassword in APIConfiguration class");
        Arrays.fill(password, '0');
        log.debug("Exiting method cleanPassword in APIConfiguration class");
    }
}