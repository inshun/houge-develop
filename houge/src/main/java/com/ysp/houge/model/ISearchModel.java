package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： 搜索model层接口
 *
 * @ClassName: ISearchModel
 * 
 * @author: hx
 * 
 * @date: 2015年12月6日 上午10:19:40
 * 
 *        版本: 1.0
 */
public interface ISearchModel {

	/** 获取搜索历史 */
	void getSearchHistory(int type, OnNetResponseCallback onNetResponseCallback);

	/** 删除搜索历史 */
	void delSearchHistory(int type, OnNetResponseCallback onNetResponseCallback);

	/** 搜索商品 */
	void searchGood(int page,String text, int type, OnNetResponseCallback onNetResponseCallback);

	/** 搜索用户 */
	void searchUser(int page,String text, OnNetResponseCallback onNetResponseCallback);
}
