package com.ysp.houge.model.entity.bean;

import java.io.Serializable;

/**
 * 描述： 附近页面banner实体
 *
 * @ClassName: BannerEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月11日 下午11:14:17
 * 
 *        版本: 1.0
 */
public class BannerEntity implements Serializable {
	private String pic;
	private String url;

	public BannerEntity() {
	}

	public BannerEntity(String pic, String url) {
		super();
		this.pic = pic;
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
