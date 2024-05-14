package com.example.wareHousePlaces.security;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * Configuration class for Spring Security.
 * <p>
 * This class defines security configurations, including authentication,
 * authorization rules, and password encoding. It uses a custom
 * UserDetailsService to load user details based on the provided username.
 * <p>
 * The class also configures security filters and rules using HttpSecurity.
 * Security rules specify which roles are required to access certain endpoints.
 * Additionally, a PasswordEncoder bean is configured for encrypting passwords.
 * <p>
 * Important! This is a demo version. The security configuration is limited to
 * a minimum to keep the connection to the frontend simple. In production,
 * further elements must be implemented such as a token generator and https.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2023-12-21
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                if ("username".equals(username)) {
                    return org.springframework.security.core.userdetails.User.builder()
                            .username(username)
                            .password(passwordEncoder.encode("password"))
                            .roles("ADMIN")
                            .build();
                } else {
                    throw new UsernameNotFoundException("User not found: " + username);
                }
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/getFreePlace/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/searchPlace/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }


}
