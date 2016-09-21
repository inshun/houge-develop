package com.ysp.houge.ui.iview;

/**
 * @描述:预支工资第二步的view层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月20日下午4:19:20
 * @version 1.0
 */
public interface IMePrePaidStepTwoView extends IBaseView {
	/**
	 * @描述:显示勤工俭学证明图片
	 * @方法名: showWorkStudyImage
	 * @param url
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午4:21:49
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午4:21:49
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showWorkStudyImage(String url);

	/**
	 * @描述:显示贫困生证明图片
	 * @方法名: showPoorProveImage
	 * @param url
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午4:21:51
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午4:21:51
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showPoorProveImage(String url);

	/**
	 * @描述:显示学习成绩单证明图片
	 * @方法名: showStudyScoreImage
	 * @param url
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午4:21:54
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午4:21:54
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showStudyScoreImage(String url);

	/**
	 * @描述:显示上传更多的图片
	 * @方法名: showMoreImage
	 * @param url
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午4:21:56
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午4:21:56
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showMoreImage(String url);

	/**
	 * @描述: 提交申请成功后进入下一页面
	 * @方法名: jumpToNextPage
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午4:23:31
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午4:23:31
	 * @修改备注
	 * @since
	 * @throws
	 */
	void jumpToNextPage();
}
