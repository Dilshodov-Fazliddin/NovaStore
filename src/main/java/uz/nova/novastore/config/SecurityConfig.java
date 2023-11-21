package uz.nova.novastore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import uz.nova.novastore.jwt.JwtTokenFilter;
import uz.nova.novastore.service.AuthenticationService;
import uz.nova.novastore.jwt.JwtService;
import uz.nova.novastore.service.UserService;
import uz.nova.novastore.service.impl.UserServiceImpl;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserServiceImpl userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    public SecurityConfig(@Lazy UserServiceImpl userService) {
        this.userService = userService;
    }

    private final String[] permitAll = {"/swagger-ui/**", "/v3/api-docs/**", "/nova/user/auth/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("*");
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.addAllowedHeader("*");
                    return corsConfiguration;
                }))
                .csrf(AbstractHttpConfigurer::disable)

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requestsConfigurer) ->
                        requestsConfigurer
                                .requestMatchers(permitAll).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenFilter(jwtService, authenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
