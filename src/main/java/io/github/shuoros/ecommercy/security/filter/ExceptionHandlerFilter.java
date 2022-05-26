package io.github.shuoros.ecommercy.security.filter;

import io.github.shuoros.ecommercy.control.util.Response;
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
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, res);
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, res, "An error occurred while fetching name from Token!");
        } catch (ExpiredJwtException e) {
            log.error("JwtException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, res, "The token has expired!");
        } catch (SignatureException e) {
            log.error("SignatureException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, res, "Invalid token!");
        } catch (RuntimeException e) {
            log.error("RuntimeException: {}", e.getMessage());
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, res, "Unexpected error!");
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
