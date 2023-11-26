package com.example.myshop.security;

import com.example.myshop.admin.domain.AdminReader;
import com.example.myshop.filter.TokenAuthenticationFilter;
import com.example.myshop.utils.TokenProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            AdminReader adminReader,
            TokenProvider tokenProvider
    ) throws Exception {
        http
                .sessionManagement(configurer -> configurer.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                new TokenAuthenticationFilter(
                        userDetailsService(adminReader),
                        tokenProvider
                ),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(AdminReader adminReader) {
        return username -> adminReader.findByUsername(username).map(AdminPrincipal::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException(username));

    }
}
