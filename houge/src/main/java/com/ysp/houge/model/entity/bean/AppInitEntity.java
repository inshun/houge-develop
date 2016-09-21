package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： app初始化实体类
 *
 * @ClassName: AppInitEntity
 * 
 * @author: hx
 * 
 * @date: 2015年12月23日 上午10:42:27
 * 
 *        版本: 1.0
 */
public class AppInitEntity implements Serializable {

	private static final long serialVersionUID = -8999424085419676633L;

	public String weixin;
	public String mobile;
	public List<String> user_bg_image;
    public int notice_new_time;
}
