package com.example.adminvansales.model;

import com.google.gson.annotations.SerializedName;

public class ErrorHandler {
    @SerializedName("ErrorCode")
    private String errorCode;
    @SerializedName("ErrorDesc")
    private  String errorDics;

    public ErrorHandler() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDics() {
        return errorDics;
    }

    public void setErrorDics(String errorDics) {
        this.errorDics = errorDics;
    }
}
