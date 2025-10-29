package com.example.emailsender.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
public class EmailSecurity {

    // 💡 ВАЖНО: Укажите здесь фактический IP-адрес вашего прокси-сервера
    private static final String PROXY_IP_ADDRESS = "192.168.1.100";
    // Если оба сервиса на localhost, можно использовать "127.0.0.1"

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для API
                .authorizeHttpRequests(auth -> auth

                        // 1. Разрешить доступ к /send-email ТОЛЬКО с указанного IP-адреса
                        .requestMatchers("/send-email")
                        .access(new WebExpressionAuthorizationManager("hasIpAddress('" + PROXY_IP_ADDRESS + "')"))

                        // 2. Все остальные запросы к другим URL, которые не были явно разрешены выше,
                        //    будут требовать аутентификации или будут запрещены (если вы используете .anyRequest().denyAll())
                        .anyRequest().denyAll() // Запретить все остальные пути (или разрешить, если нужно)
                );

        return http.build();
    }
}
