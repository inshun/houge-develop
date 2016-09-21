package com.ysp.houge.presenter;

import android.os.Bundle;

/**
 * 描述： 完善注册信息Presenter层接口
 *
 * @ClassName: IPerfectRegisterInfoPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月24日 下午1:11:31
 * 
 *        版本: 1.0
 */
public interface IPerfectRegisterInfoPresenter {
	/** 从上一页面获取数据 */
	void getDataFromFormaPage(Bundle bundle);

	/** 点击头像布局 */
	void clickAvatarLayout();

	/** 选择图片返回 */
	void choosePictureBack(String cropAvatarPath);

	/** 上传头像 */
	void uploadAvatar();

	/** 更换性别 */
	void changeSex();

	/** 完成 */
	void clickRegisterBtn(String nickName, String recommentCode, int uid);

	/** 注册请求 */
	void requestRegister(String image_url);

    /** 推送信息收集请求 */
    void pushInfoRequest();
}
