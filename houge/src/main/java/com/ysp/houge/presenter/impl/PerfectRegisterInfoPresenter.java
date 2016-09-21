package com.ysp.houge.presenter.impl;

import android.os.Bundle;
import android.text.TextUtils;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.IFileUploadModel;
import com.ysp.houge.model.IPushInfoModel;
import com.ysp.houge.model.IRegisterAndLoginModel;
import com.ysp.houge.model.entity.bean.PictureEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.FileUploadModelImpl;
import com.ysp.houge.model.impl.PushInfoModelImpl;
import com.ysp.houge.model.impl.RegisterAndLoginModel;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IPerfectRegisterInfoPresenter;
import com.ysp.houge.ui.iview.IPerfectRegisterInfoPageView;
import com.ysp.houge.utility.ImageReduceUtils;
import com.ysp.houge.utility.RsaUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * 描述： 完善注册信息Presenter层
 *
 * @ClassName: PerfectRegisterInfoPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月24日 下午1:12:16
 * 
 *        版本: 1.0
 */
public class PerfectRegisterInfoPresenter extends BasePresenter<IPerfectRegisterInfoPageView>
		implements IPerfectRegisterInfoPresenter {

    public final static String KEY_PHONE_NUM = "phone";
	public final static String KEY_CODE = "code";
	public final static String KEY_PASSWORD = "password";

	private String cropAvatarPath;// 头像地址
	private String nickname; // 昵称
	private String mobile; // 手机号
	private String password;// 密码
	private String code;// 验证码
	private String invite_id;// 推荐码
	private int sex = UserInfoEntity.SEX_MAL;// 性别(默认男)

    //推送信息收集接口
    private IPushInfoModel iPushInfoModel;
    //文件上传model（头像）
	private IFileUploadModel iFileUploadModel;
    //注册model
	private IRegisterAndLoginModel iRegisterAndLoginModel;
    private int Uid;

    public PerfectRegisterInfoPresenter(IPerfectRegisterInfoPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
        iPushInfoModel = new PushInfoModelImpl();
		iFileUploadModel = new FileUploadModelImpl();
		iRegisterAndLoginModel = new RegisterAndLoginModel();
	}

	@Override
	public void getDataFromFormaPage(Bundle bundle) {
		// 获取注册页面的 手机号验证码 以及密码信息
		if (bundle != null) {
			if (bundle.containsKey(KEY_PHONE_NUM)) {
				mobile = bundle.getString(KEY_PHONE_NUM);
			}else {
                mView.showToast("异常的注册信息");
                mView.close();
            }

			if (bundle.containsKey(KEY_CODE)) {
				code = bundle.getString(KEY_CODE);
			}else {
                mView.showToast("异常的注册信息");
                mView.close();
            }

			if (bundle.containsKey(KEY_PASSWORD)) {
				password = bundle.getString(KEY_PASSWORD);
			}else {
                mView.showToast("异常的注册信息");
                mView.close();
            }
		} else {
			mView.showToast("异常的注册信息");
			mView.close();
		}
	}

	@Override
	public void clickAvatarLayout() {
        //选择头像
		mView.choosePicture();
	}

	@Override
	public void choosePictureBack(String cropAvatarPath) {
		this.cropAvatarPath = cropAvatarPath;
        //显示头像，显示前加上本地的显示前缀
		mView.setAvatarAndSex("file://" + cropAvatarPath, sex);
	}

	@Override
	public void changeSex() {
        //改变性别
		sex = sex == UserInfoEntity.SEX_MAL ? UserInfoEntity.SEX_FEMAL : UserInfoEntity.SEX_MAL;
        if (TextUtils.isEmpty(cropAvatarPath)){
		    mView.setAvatarAndSex("",sex);
        }else {
		    mView.setAvatarAndSex("file://" + cropAvatarPath,sex);
        }
	}

	@Override
	public void clickRegisterBtn(String nickName, String recommentCode, int uid) {
        //检查头像有没有设置
		if (TextUtils.isEmpty(cropAvatarPath)) {
			mView.showToast(R.string.set_avatar);
			return;
		}

        //检查昵称有没有设置
		if (TextUtils.isEmpty(nickName)) {
			mView.showToast(R.string.set_name);
			return;
		} else {
			// 限制昵称长度(2~8个字符之间,最长已经在xml中设置)
			if (nickName.length() < 2) {
				mView.showToast("昵称必须在2~8个字符");
				return;
			} else {
				this.nickname = nickName;
			}
		}

        //为推荐码赋值（推荐码可有可无，所以不做强制检查）
//		if (!TextUtils.isEmpty(recommentCode)) {
//			invite_id = recommentCode;
//		}

        Uid = uid;
        //检查完所有的信息之后上传图片
		uploadAvatar();
	}


    @Override
	public void uploadAvatar() {
		mView.showProgress();
		iFileUploadModel.uploadSingleFile(0, ImageReduceUtils.compression(cropAvatarPath), "path", "avatar",
                new OnNetResponseCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        mView.hideProgress();
                        if (object != null && object instanceof PictureEntity) {
                            PictureEntity pictureEntity = (PictureEntity) object;
                            // 头像上传成功后去注册（拿到图像的地址）
                            requestRegister(pictureEntity.getPath());
                        } else {
                            mView.showToast(R.string.request_failed);
                        }

                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        mView.hideProgress();
                        switch (errorCode) {
                            case ResponseCode.TIP_ERROR:
                                mView.showToast(message);
                                break;
                            default:
                                mView.showToast("您的网络有问题，请检查后再试");
                                break;
                        }
                    }
                });
	}

	@Override
	public void requestRegister(String image_url) {
		mView.showProgress();
		iRegisterAndLoginModel.registerRequest(mobile, code, RsaUtils.encryptByPublic(password), nickname, image_url, String.valueOf(sex),
                invite_id, new OnNetResponseCallback() {

                    @Override
                    public void onSuccess(Object t) {
                        mView.hideProgress();
                        if (t != null && t instanceof UserInfoEntity) {
                            UserInfoEntity userInfo = (UserInfoEntity) t;
                            // 如果没有返回token提示获取失败
                            if (TextUtils.isEmpty(userInfo.token)) {
                                mView.showToast(R.string.request_token_failed);
                            } else {
                                // 保存token
                                MyApplication.getInstance().getPreferenceUtils().setToken(userInfo.token);
                                // 保存登陆的用户对象
                                MyApplication.getInstance().getPreferenceUtils().setId(userInfo.id);
                                // 将用户登陆数据保存到数据库库
                                UserInfoController.createOrUpdate(userInfo);

                                // 发送推送统计后不管成功失败进入主页面
                                pushInfoRequest();
                            }
                        } else {
                            mView.showToast(R.string.request_failed);
                        }
                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        mView.hideProgress();
                        switch (errorCode) {
                            case ResponseCode.TIP_ERROR:
                                mView.showToast(message);
                                break;
                            default:
                                mView.showToast(R.string.register_failed);
                                break;
                        }
                    }
                });
	}

    @Override
    public void pushInfoRequest() {
        iPushInfoModel.setPushInfo(0, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                mView.showToast("注册成功");
                mView.jumpToHomePage(Uid);
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.showToast("注册成功");
                mView.jumpToHomePage(Uid);
            }
        });
    }
}
