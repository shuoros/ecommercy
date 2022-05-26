package io.github.shuoros.ecommercy.aspect;

import io.github.shuoros.ecommercy.control.util.Request;
import io.github.shuoros.ecommercy.dao.repository.AdminRepository;
import io.github.shuoros.ecommercy.dao.repository.UserRepository;
import io.github.shuoros.ecommercy.exception.PayloadException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class AuthControllerAspects {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public AuthControllerAspects(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Before(value = "execution(* io.github.shuoros.ecommercy.control.AuthController..*(..)) && args(request, payload)",//
            argNames = "joinPoint,request,payload")
    public void logRequests(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        logRequest(joinPoint, request, payload);
    }

    @Before(value = "execution(* io.github.shuoros.ecommercy.control.AuthController.authenticateAdmin(..)) && args(request, payload)",//
            argNames = "joinPoint,request,payload")
    public void beforeAuthenticateAdmin(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        final JSONObject json = Request.deserialize(payload);
        if (json == null) throw new PayloadException("JSON syntax error!");
        if (Request.notHasKeys(json, "username", "password"))
            throw new PayloadException("Missing username or password!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (adminRepository.findByEmail(json.getString("username")).isEmpty())
            throw new UsernameNotFoundException("Invalid username or password!");
    }

    @Before(value = "execution(* io.github.shuoros.ecommercy.control.AuthController.authenticateUser(..)) && args(request, payload)",//
            argNames = "joinPoint,request,payload")
    public void beforeAuthenticateUser(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        final JSONObject json = Request.deserialize(payload);
        if (json == null) throw new PayloadException("JSON syntax error!");
        if (Request.notHasKeys(json, "username", "password"))
            throw new PayloadException("Missing username or password!", HttpStatus.UNPROCESSABLE_ENTITY);
        if (userRepository.findByEmail(json.getString("username")).isEmpty())
            throw new UsernameNotFoundException("Invalid username or password!");
    }

    private void logRequest(JoinPoint joinPoint, HttpServletRequest request, String payload) {
        log.info("{}: request: {}, ip: {}, user-agent: {}\n{}",//
                joinPoint.toShortString(),//
                request.getRequestURI(),//
                request.getRemoteAddr(),//
                request.getHeader("User-Agent"),//
                payload);
    }

}
