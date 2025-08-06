package com.product.utils;

import org.springframework.http.HttpStatus;

import com.product.entity.UserLog;

public class LogConverter {

    public static void updateUserLog(UserLog userLog, HttpStatus httpStatus, ResponseStatus responseStatus, Object responseData, String message) {
        userLog.setHttpStatus(httpStatus.value());
        userLog.setResponseStatus(responseStatus.name());
        userLog.setResponseData(responseData);
        userLog.setMessage(message);
    }
}