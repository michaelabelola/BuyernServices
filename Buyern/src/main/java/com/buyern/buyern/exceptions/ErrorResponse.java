package com.buyern.buyern.exceptions;


import com.buyern.buyern.dto.ResponseDTO;

public class ErrorResponse extends ResponseDTO<Object> {

    public ErrorResponse(String code, String message, Object data) {
        super(code, message, data);
    }

    public ErrorResponse(String code, String message) {
        super(code, message);
    }
}
