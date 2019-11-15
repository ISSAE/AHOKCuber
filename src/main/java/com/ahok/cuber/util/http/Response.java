package com.ahok.cuber.util.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    private static String body(boolean success, Object body, HttpStatus status) {
        return JSON.toJSON(new ResponseBody(success, body, status));
    }

    public static ResponseEntity ok(Object body) {
        return ResponseEntity.ok(Response.body(true, body, HttpStatus.OK));
    }

    public static ResponseEntity internalError(Object body) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.body(false, body, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public static ResponseEntity badRequest(Object object) {
        return ResponseEntity.badRequest().body(Response.body(false, object, HttpStatus.BAD_REQUEST));
    }
}
