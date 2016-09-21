package com.ysp.houge.presenter;

/**
 * @描述:预支工资第二步
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月20日下午3:44:13
 * @version 1.0
 */
public interface IMePrePaidStepTwoPresenter {

	/**
	 * @描述:获取上一页面的数据
	 * @方法名: getDataFromFormalPage
	 * @param advanceMoney
	 * @param collectionAccount
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午4:56:59
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午4:56:59
	 * @修改备注
	 * @since
	 * @throws
	 */
	void getDataFromFormalPage(String advanceMoney, String collectionAccount);

	/**
	 * @描述:点击提交按钮
	 * @方法名: clickSubmitBtn
	 * @param number
	 *            担保人数量
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午3:53:18
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午3:53:18
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clickSubmitBtn(String number);

	/**
	 * @描述:展示是否提交申请的对话框
	 * @方法名: showWhetherSubmitDialog
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午3:54:19
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午3:54:19
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showWhetherSubmitDialog();

	/**
	 * @描述:进行申请请求
	 * @方法名: sureSubmitRequest
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午3:54:39
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午3:54:39
	 * @修改备注
	 * @since
	 * @throws
	 */
	void sureSubmitRequest();

	/**
	 * @描述:选择勤工俭学证明图片返回
	 * @方法名: chooseWorkStudyImageBack
	 * @param path
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午5:02:29
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午5:02:29
	 * @修改备注
	 * @since
	 * @throws
	 */
	void chooseWorkStudyImageBack(String path);

	/**
	 * @描述:选择贫困生证明图片返回
	 * @方法名: choosePoorProveImageBack
	 * @param path
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午5:02:18
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午5:02:18
	 * @修改备注
	 * @since
	 * @throws
	 */
	void choosePoorProveImageBack(String path);

	/**
	 * @描述:选择学习成绩单图片返回
	 * @方法名: chooseStudyScoreImageBack
	 * @param path
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午5:02:06
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午5:02:06
	 * @修改备注
	 * @since
	 * @throws
	 */
	void chooseStudyScoreImageBack(String path);

	/**
	 * @描述:选择上传更多图片返回
	 * @方法名: chooseMoreImageBack
	 * @param path
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月20日下午5:01:50
	 * @修改人 tyn
	 * @修改时间 2015年9月20日下午5:01:50
	 * @修改备注
	 * @since
	 * @throws
	 */
	void chooseMoreImageBack(String path);
}
