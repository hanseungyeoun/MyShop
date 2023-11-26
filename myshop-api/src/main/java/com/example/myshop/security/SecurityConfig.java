package com.example.myshop.security;

import com.example.myshop.filter.RestAuthenticationEntryPoint;
import com.example.myshop.filter.TokenAuthenticationFilter;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.member.interfaces.MemberPrincipal;
import com.example.myshop.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SecurityConfig {

    private final RestAuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            MemberReader memberReader,
            TokenProvider tokenProvider
    ) throws Exception {
        http
                .sessionManagement(configurer -> configurer.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(error -> error.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .addFilterBefore(
                        new TokenAuthenticationFilter(
                                userDetailsService(memberReader),
                                tokenProvider
                        ),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberReader memberReader) {
        return username -> memberReader.findByUsername(username).map(MemberPrincipal::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
