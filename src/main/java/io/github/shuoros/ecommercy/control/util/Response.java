package io.github.shuoros.ecommercy.control.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "ok", "status", "error", "message", "data", "path"})
public class Response {

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime timestamp;
    private boolean ok;
    private int status;
    private String error;
    private String message;
    private Object data;
    private String path;

    public static Response success(String message, Object data, HttpStatus status, String path) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(true)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .data(data)//
                .path(path)//
                .build();
    }

    public static Response success(String message, HttpStatus status, String path) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(true)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .path(path)//
                .build();
    }

    public static Response success(String message, Object data, HttpStatus status) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(true)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .data(data)//
                .build();
    }

    public static Response success(String message, HttpStatus status) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(true)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .build();
    }

    public static Response error(String message, Object data, HttpStatus status, String path) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(false)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .data(data)//
                .path(path)//
                .build();
    }

    public static Response error(String message, HttpStatus status, String path) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(false)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .path(path)//
                .build();
    }

    public static Response error(String message, Object data, HttpStatus status) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(false)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .data(data)//
                .build();
    }

    public static Response error(String message, HttpStatus status) {
        return Response.builder()//
                .timestamp(ZonedDateTime.now())//
                .ok(false)//
                .status(status.value())//
                .error(status.getReasonPhrase())//
                .message(message)//
                .build();
    }

    public ResponseEntity<String> serialize() {
        return new ResponseEntity<>(toJson(), HttpStatus.valueOf(this.status));
    }

    @SneakyThrows
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(this);
    }

}
