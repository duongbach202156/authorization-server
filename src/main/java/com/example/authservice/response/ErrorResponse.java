package com.example.authservice.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends GenericResponse<Object>{
    private List<String> errors = null;

    private String path = null;

    public ErrorResponse(List<String> errors, String message, Integer code, String path) {
        super(null, code, message, false);
        this.errors = errors;
        this.path = path;
    }
}
