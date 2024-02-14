package ru.rsreu.ChineseCourse.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain configureHttp(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/login", "/registration").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/user")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/logout")
                        .invalidateHttpSession(true) // Удаление данных сессии
                        .deleteCookies("JSESSIONID")
                        .permitAll());


        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
