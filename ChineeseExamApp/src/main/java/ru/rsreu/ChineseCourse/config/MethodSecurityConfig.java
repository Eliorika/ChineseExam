package ru.rsreu.ChineseCourse.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import ru.rsreu.ChineseCourse.security.CustomSecurityExpressionHandler;
import ru.rsreu.ChineseCourse.service.IUserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    private IUserService userService;


    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new CustomSecurityExpressionHandler(userService);
    }
}
