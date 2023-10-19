package com.example.jache.security;

import com.example.jache.constant.handler.CustomAccessDeniedHandler;
import com.example.jache.constant.handler.CustomAuthenticationEntryPoint;
import com.example.jache.constant.handler.CustomExceptionHandler;
import com.example.jache.security.jwtTokens.JwtAuthenticationFilter;
import com.example.jache.security.jwtTokens.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //spring security 활성화
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig{

    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session->session
                                .sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS)
                )//jwt토큰이라 세션 설정 false
                //권한 설정
                .authorizeHttpRequests((authorizeHttprequests) -> authorizeHttprequests
                        .requestMatchers("/auth/**").hasRole("USER")
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/member/**").hasRole("USER")
                        .requestMatchers("/ws-jache").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling((httpSecutiryExceptionHandler)
                        -> httpSecutiryExceptionHandler
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtTokenUtil);
    }


}
