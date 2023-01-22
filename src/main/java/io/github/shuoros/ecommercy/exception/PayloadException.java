package io.github.shuoros.ecommercy.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PayloadException extends RuntimeException {

    private final HttpStatus status;

    public PayloadException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public PayloadException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
