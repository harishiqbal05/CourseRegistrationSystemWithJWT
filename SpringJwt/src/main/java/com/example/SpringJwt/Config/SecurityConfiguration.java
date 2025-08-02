package com.example.SpringJwt.Config;

import com.example.SpringJwt.Filter.JwtAuthenticationFilter;
import com.example.SpringJwt.Model.Role;
import com.example.SpringJwt.Service.JwtService;
import com.example.SpringJwt.Service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    UserDetailServiceImpl userdetailserviceimpl;
    JwtAuthenticationFilter jwtaunthenticationfilter;

    public SecurityConfiguration(UserDetailServiceImpl userdetailserviceimpl, JwtAuthenticationFilter jwtaunthenticationfilter) {
        this.userdetailserviceimpl = userdetailserviceimpl;
        this.jwtaunthenticationfilter = jwtaunthenticationfilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/register/**", "/login/**").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/courses/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest()
                        .authenticated()
                ).userDetailsService(userdetailserviceimpl)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtaunthenticationfilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}