package io.github.shuoros.ecommercy.control.util;

import lombok.Builder;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
public class Response {

    private boolean ok;
    private String message;
    private HttpStatus status;
    private Object data;
    private Date timestamp;

    public static Response success(String message, Object data, HttpStatus status) {
        return Response.builder()
                .ok(true)
                .message(message)
                .status(status)
                .data(data)
                .timestamp(new Date())
                .build();
    }

    public static Response success(String message, HttpStatus status) {
        return Response.builder()
                .ok(true)
                .message(message)
                .status(status)
                .timestamp(new Date())
                .build();
    }

    public static Response error(String message, Object data, HttpStatus status) {
        return Response.builder()
                .ok(false)
                .message(message)
                .status(status)
                .data(data)
                .timestamp(new Date())
                .build();
    }

    public static Response error(String message, HttpStatus status) {
        return Response.builder()
                .ok(false)
                .message(message)
                .status(status)
                .timestamp(new Date())
                .build();
    }

    public ResponseEntity<String> serialize() {
        JSONObject json = new JSONObject();
        json.put("ok", this.ok);
        json.put("message", this.message);
        json.put("status", this.status.value());
        json.put("data", this.data);
        json.put("timestamp", new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SS").format(this.timestamp));
        return new ResponseEntity<>(json.toString(), this.status);
    }

}
