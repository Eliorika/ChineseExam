package ru.rsreu.ChineseCourse.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import ru.rsreu.ChineseCourse.model.User;
import ru.rsreu.ChineseCourse.model.enums.SystemRole;
import ru.rsreu.ChineseCourse.service.IUserService;

import java.util.function.Supplier;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private final IUserService userService;
    private Object filterObject;
    private Object returnObject;
    private Object target;
    private static final Logger log =
            LoggerFactory.getLogger(CustomMethodSecurityExpressionRoot.class);

    public CustomMethodSecurityExpressionRoot(Authentication authentication,
            IUserService userService) {
        super(authentication);
        this.userService = userService;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return target;
    }


    public boolean isSuperAdmin() {
        log.debug("isSuperAdmin");
        User user = userService.findByEmail(this.getAuthentication().getName());
        if (user.getSystemRole().equals(SystemRole.ROLE_SUPER_ADMIN)) {
            log.debug("User is super admin");
            return true;
        } else {
            log.debug("User is NOT super admin");
            return false;
        }
    }
}
