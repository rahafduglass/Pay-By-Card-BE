package com.internship.paybycard.paymentprocess;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    private static final String SECRET_KEY = "T6lBXXy6IFPfTeXjygHQI20SWUH1gcx3rwdDfU7WVjRbD4YAlbQoS6YAB9dD3yjyl+LzZJOKVpVJeRZ0+Ys6tg==";

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
        return NimbusJwtDecoder.withSecretKey(new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256")).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**"
                        ).permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
        //TODO add jwt filter when done
        ;
        return http.build();
    }
}
