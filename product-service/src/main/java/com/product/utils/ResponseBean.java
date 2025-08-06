package com.product.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseBean {
	
	public ResponseBean(String apiName, ResponseStatus status, String resultString) {
        this.apiName = apiName;
        this.status = status;
        this.resultString = resultString;
    }
	 private String apiName;
	    private ResponseStatus status;
	    private String resultString;
	    private Object data;
   }
