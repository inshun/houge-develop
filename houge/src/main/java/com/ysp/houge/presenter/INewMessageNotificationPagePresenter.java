package com.ysp.houge.presenter;

/**
 * @author it_hu
 * 
 *         新消息设置Presneter层接口
 *
 */
public interface INewMessageNotificationPagePresenter {
	/** 获取提醒设置 */
	void getNoteSetting();

	/** 更改提醒设置 */
	void changeNote(int index);

	void submit();
}
