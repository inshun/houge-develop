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
package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * 描述： 网页实体
 *
 * @ClassName: WebEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月12日 下午1:03:30
 * 
 *        版本: 1.0
 */
public class WebEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url;
	private String title;

	public WebEntity(String url) {
		super();
	}

	public WebEntity(String url, String title) {
		super();
		this.url = url;
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
