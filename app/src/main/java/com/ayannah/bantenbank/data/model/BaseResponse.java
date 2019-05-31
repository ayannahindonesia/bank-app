package com.ayannah.bantenbank.data.model;

public class BaseResponse {

    private boolean error;
    private String message;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
