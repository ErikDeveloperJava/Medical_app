package net.medical.interceptor;

import net.medical.config.security.UserDetailsImpl;
import net.medical.model.User;
import net.medical.model.enums.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(authentication.getPrincipal() instanceof UserDetailsImpl){
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            user = userDetails.getUser();
        }else {
            user = User.builder().role(UserRole.ROLE_ANONYMOUS).build();
        }
        request.setAttribute("user",user);
        LOGGER.debug("user : {}",user);
        return true;
    }
}
