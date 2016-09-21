/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.model.entity.base;

import com.google.gson.annotations.SerializedName;

public class BaseHttpResultEntity<T> {

	@SerializedName(value = "result")
	private boolean result;

	@SerializedName(value = "data")
	private T data;
	
	@SerializedName(value = "msg")
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseHttpResultEntity [result=" + result + ", data=" + data + ", msg=" + msg + "]";
	}

}
