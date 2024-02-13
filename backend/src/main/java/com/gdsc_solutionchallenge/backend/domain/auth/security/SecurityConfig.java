package com.gdsc_solutionchallenge.backend.domain.auth.security;

import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtAuthenticationFilter;
import com.gdsc_solutionchallenge.backend.domain.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity // Enable method-level security settings
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Configure the security filter chain.
     *
     * @param http HttpSecurity object to configure security settings.
     * @return SecurityFilterChain object representing the configured security filter chain.
     * @throws Exception if configuration encounters an exception.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable) // Disable basic authentication and CSRF for REST API
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use JWT, disable session creation
                .authorizeRequests()
                .requestMatchers(
                        // -- Swagger UI v3 (OpenAPI)
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**").permitAll()
                // Allow all requests for specific paths without authentication
                .requestMatchers(new AntPathRequestMatcher("/members/sign-in")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/members/sign-up")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/members/test")).hasRole("USER") // Require USER role for /members/test
                .anyRequest().authenticated() // Require authentication for all other requests
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configure the password encoder bean using BCrypt.
     *
     * @return PasswordEncoder object representing the configured password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
