package io.github.shuoros.ecommercy.control;

import io.github.shuoros.ecommercy.control.util.Response;
import io.github.shuoros.ecommercy.exception.PayloadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(PayloadException.class)
    public ResponseEntity<String> payloadExceptionController(PayloadException e) {
        return Response.error(e.getMessage(), e.getStatus()).serialize();
    }

    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<String> wrongUserNamePasswordExceptionController(Exception e) {
        return Response.error("Invalid username or password!", HttpStatus.UNAUTHORIZED).serialize();
    }

}