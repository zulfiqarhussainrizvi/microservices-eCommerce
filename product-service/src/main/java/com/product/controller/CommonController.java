package com.product.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.product.entity.UserLog;
import com.product.utils.LogConverter;
import com.product.utils.LogTaskExecutor;
import com.product.utils.ResponseBean;
import com.product.utils.ResponseStatus;

public class CommonController {
	
	@Autowired
	private LogTaskExecutor logTaskExecutor;

    protected <T> ResponseEntity<ResponseBean> sendSuccessResponse(UserLog userLog, String message, T data) {
        ResponseBean responseBean = new ResponseBean(userLog.getApiName(), ResponseStatus.SUCCESSFUL, message);
        responseBean.setData(data);
        return sendResponse(responseBean, userLog, HttpStatus.OK, ResponseStatus.SUCCESSFUL, message);
    }

    protected ResponseEntity<ResponseBean> sendHttpBadReqResponse(UserLog userLog, String reason) {
        ResponseBean responseBean = new ResponseBean(userLog.getApiName(), ResponseStatus.FAILED, reason);
        return sendResponse(responseBean, userLog, HttpStatus.BAD_REQUEST, ResponseStatus.FAILED, reason);
    }

    protected <T> ResponseEntity<T> sendResponse(
            T object,
            UserLog userLog,
            HttpStatus httpStatus,
            ResponseStatus respStatus,
            String message) {

        LogConverter.updateUserLog(userLog, httpStatus, respStatus, object, message);
        logTaskExecutor.offerLog(userLog); // ✅ Correct usage

        return new ResponseEntity<>(object, httpStatus);
    }
}