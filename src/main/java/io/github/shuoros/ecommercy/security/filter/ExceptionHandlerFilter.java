package io.github.shuoros.ecommercy.security.filter;

import io.github.shuoros.ecommercy.control.util.Response;
import io.github.shuoros.ecommercy.exception.PayloadException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res);
        } catch (IllegalArgumentException e) {
            log.warn("IllegalArgumentException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, req, res, "An error occurred while fetching name from Token!");
        } catch (ExpiredJwtException e) {
            log.warn("JwtException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, req, res, "The token has expired!");
        } catch (SignatureException e) {
            log.warn("SignatureException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, req, res, "Invalid token!");
        } catch (PayloadException e) {
            log.warn("PayloadException: {}", e.getMessage());
            setErrorResponse(e.getStatus(), req, res, e.getMessage());
        } catch (RuntimeException e) {
            log.warn("RuntimeException: {}", e.getMessage());
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, req, res, "Unexpected error!");
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest req, HttpServletResponse res, String message) {
        res.setStatus(status.value());
        res.setContentType("application/json");
        try {
            res.getWriter().write(Response.error(message, status, req.getRequestURI()).toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
