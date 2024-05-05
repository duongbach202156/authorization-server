package com.example.authservice.response;

import lombok.Data;

import java.util.Date;

@Data
public class GenericResponse<D> {
    private final Date timestamp = new Date();

    private D data;

    private Integer code;

    private String message;

    private Boolean success;

    public GenericResponse() {this(null, 200, "success", true);}

    public GenericResponse(D data) {
        this(data, 200, "success", true);
    }

    public GenericResponse(D data, Integer code) {
        this(data, code, "success", true);
    }
    public GenericResponse(D data, Integer code, String message) {
        this(data, code, message, true);
    }


    public GenericResponse(D data, Integer code, String message, Boolean success) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
