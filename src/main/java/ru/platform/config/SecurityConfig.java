package ru.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.platform.service.impl.SecurityService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Bean
    public UserDetailsService getUserDetailsService() {return new SecurityService();}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    private final SecurityService securityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/user/mainPage",
                                        "/user/createUser",
                                        "/user/getSignupForm",
                                        "/login","/css/**",
                                        "/js/**","/images/**").permitAll()  // todo поправить доступы
                                .anyRequest()
                                .authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/user/mainPage",true))
                .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(securityService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService);
    }
}