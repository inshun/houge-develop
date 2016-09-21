package com.ysp.houge.presenter;

/**
 * @描述: 工种列表页面Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年8月30日下午6:00:05
 * @version 1.0
 */
public interface IWorkTypeListPagePresenter {
	/**设置关注字符串*/
	void setRecommendStr(String str);
	
	/** 获取工种列表 */
	void getWorkTypeList(int buy);

	/** 提交 */
	void submit(int buy);
	
	/** itemClick */
	void itemClick(int position);

}
