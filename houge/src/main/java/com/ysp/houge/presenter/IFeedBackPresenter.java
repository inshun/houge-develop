package com.ysp.houge.presenter;

public interface IFeedBackPresenter {
	void requstSubmitFeedBack();

	/**
	 * @描述:检查是否可以反馈
	 * @方法名: checkCanFeedbackState
	 * @param content
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年7月19日上午11:16:56
	 * @修改人 tyn
	 * @修改时间 2015年7月19日上午11:16:56
	 * @修改备注
	 * @since
	 * @throws
	 */
	void checkCanFeedbackState(String content);
}
