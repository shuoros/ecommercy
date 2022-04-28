package io.github.shuoros.ecommercy.security.filter;

import io.github.shuoros.ecommercy.control.util.ApiError;
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
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, "An error occurred while fetching name from Token!");
        } catch (ExpiredJwtException e) {
            log.error("JwtException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, "The token has expired!");
        } catch (SignatureException e) {
            log.error("SignatureException: {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, "Authentication Failed. Name or Password not valid!");
        } catch (RuntimeException e) {
            log.error("RuntimeException: {}", e.getMessage());
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, response, "Unexpected error!");
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest request, HttpServletResponse response, String message) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiError apiError = new ApiError(status, request.getRequestURI(), message);
        try {
            response.getWriter().write(apiError.convertToJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
