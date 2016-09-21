package com.ysp.houge.popwindow;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.adapter.MapNeedAdapter;
import com.ysp.houge.adapter.MapSkillAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnMapHaveATalkClickListener;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.MyNeedEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.ui.HomeActivity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.List;
/**
 * 描述： 地图页面的弹窗
 *
 * @ClassName: MapPopupWindow
 *
 * @author: hx
 *
 * @date: 2015年12月20日 下午11:49:50
 *
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class MapPopupWindow extends PopupWindow implements OnClickListener, OnMapHaveATalkClickListener, OnItemClickListener {
	private final String goods_info_ids;
	private int id;
	private int loginStatus;
	private Context context;
	private LayoutInflater inflater;
	private View contentView;
	private LinearLayout layout;
	private ImageView close;
	private LinearLayout info;
	private ImageView avatar, avatarBg;
	private TextView nick, rate, safeguard, auth, distance;
	private ListView listView;
	private MapNeedAdapter needAdapter;
	private MapSkillAdapter skillAdapter;
	private IUserInfoModel iUserInfoModel;
	@SuppressWarnings("deprecation")
	public MapPopupWindow(int id, String goods_info_ids, Context context) {
		super();
		this.id = id;
		this.context = context;
		this.goods_info_ids = goods_info_ids;
		inflater = LayoutInflater.from(context);
		setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		// 使其聚集
		setFocusable(true);
		// 设置允许在外点击消失
		setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setBackgroundDrawable(new BitmapDrawable());
		iUserInfoModel = new UserInfoModelImpl();
		initView();
		requsetUserInfo(id);
		// 根据登录状态加载不同的列表
		loginStatus = MyApplication.getInstance().getLoginStaus();
		switch (loginStatus) {
			case MyApplication.LOG_STATUS_BUYER:
				requestUserSkill(id,goods_info_ids);
				break;
			case MyApplication.LOG_STATUS_SELLER:
				requestUserNeed(id, goods_info_ids);
				break;
		}
		setContentView(contentView);
		showAtLocation(((HomeActivity) context).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}
	private void initView() {
		contentView = inflater.inflate(R.layout.popup_map_pop, null);
		layout = (LinearLayout) contentView.findViewById(R.id.line_map_pop);
		close = (ImageView) contentView.findViewById(R.id.iv_close);
		info = (LinearLayout) contentView.findViewById(R.id.line_user_info_layout);
		avatar = (ImageView) contentView.findViewById(R.id.iv_user_icon);
		avatarBg = (ImageView) contentView.findViewById(R.id.iv_user_icon_bg);
		info.setOnClickListener(this);
		nick = (TextView) contentView.findViewById(R.id.tv_user_name);
		rate = (TextView) contentView.findViewById(R.id.tv_rate);
		safeguard = (TextView) contentView.findViewById(R.id.tv_service_safeguard);
		auth = (TextView) contentView.findViewById(R.id.tv_auth);
		distance = (TextView) contentView.findViewById(R.id.tv_distance);
		listView = (ListView) contentView.findViewById(R.id.lv_map);
		listView.setOnItemClickListener(this);
		layout.setOnClickListener(this);
		close.setOnClickListener(this);
	}
	/** 显示用户信息 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void initUserInfo(UserInfoEntity entity) {
		if (null == entity) {
			dismiss();
			((HomeActivity) context).showToast("信息错误");
			return;
		}
		// 头像背景
		switch (entity.sex) {
			case UserInfoEntity.SEX_MAL:
				avatarBg.setImageResource(R.drawable.skill_user_icon_man);
				break;
			case UserInfoEntity.SEX_FEMAL:
				avatarBg.setImageResource(R.drawable.skill_user_icon_wuman);
				break;
			default:
				avatarBg.setImageResource(R.drawable.skill_user_icon_nosex);
				break;
		}
		// 头像
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(avatar, entity.avatar,
				LoadImageType.RoundAvatar);
		// 用户名
		if (!TextUtils.isEmpty(entity.nick)) {
			nick.setText(entity.nick);
		}
		// 名字的颜色（这里后期顺便把上方消失的图片换掉）
		switch (loginStatus) {
			case MyApplication.LOG_STATUS_BUYER:
				nick.setTextColor(context.getResources().getColor(R.color.color_app_theme_orange_bg));
				close.setBackground(context.getResources().getDrawable(R.drawable.map_close_orange));
				break;
			case MyApplication.LOG_STATUS_SELLER:
				nick.setTextColor(context.getResources().getColor(R.color.color_app_theme_blue_bg));
				close.setBackground(context.getResources().getDrawable(R.drawable.map_close_blue));
				break;
		}
		// 星级
		if (!TextUtils.isEmpty(String.valueOf(entity.star))) {
			rate.setText(String.valueOf(entity.star) + " ★");
		}
		// 服务保障
		if (TextUtils.isEmpty(entity.serviceSafeguardg)) {
			safeguard.setVisibility(View.GONE);
		} else {
			double money = -1;
			try{money = Double.parseDouble(entity.serviceSafeguardg); }catch (Exception e){}
			if (money > 0) {
				safeguard.setVisibility(View.VISIBLE);
			} else {
				safeguard.setVisibility(View.GONE);
			}
		}
		// 认证
		if (TextUtils.isEmpty(entity.verfied)) {
			auth.setVisibility(View.GONE);
		} else {
			auth.setVisibility(View.VISIBLE);
			if (TextUtils.equals("1", entity.verfied)) {
				auth.setText("学生认证");
			} else if (TextUtils.equals("2", entity.verfied)) {
				auth.setText("个人认证");
			} else if (TextUtils.equals("3", entity.verfied)) {
				auth.setText("企业认证");
			}else {
				auth.setVisibility(View.GONE);
			}
		}
	}
	/** 显示用户技能信息 */
	private void initSkills(List<GoodsDetailEntity> list) {
		if (null != list && !list.isEmpty()) {
			skillAdapter = new MapSkillAdapter(this, list, context);
			listView.setAdapter(skillAdapter);
			// 设置高度
			if (list.size() > 3) {
				View view = listView.getAdapter().getView(0, null, listView);
				view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				int height = view.getMeasuredHeight();
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height * 3);
				listView.setLayoutParams(params);
			}
		} else {
			((HomeActivity) context).showToast("用户暂未发布任何技能");
			dismiss();
		}
	}
	/** 显示用户需求信息 */
	private void initNeeds(List<GoodsDetailEntity> list) {
		if (null != list && !list.isEmpty()) {
			needAdapter = new MapNeedAdapter(this, list, context);
			listView.setAdapter(needAdapter);
			// 设置高度
			if (list.size() > 2) {
				View view = listView.getAdapter().getView(0, null, listView);
				view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
						MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
				int height = view.getMeasuredHeight();
				LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height * 2);
				listView.setLayoutParams(params);
			}
		} else {
			((HomeActivity) context).showToast("用户暂未发布任何需求");
			dismiss();
		}
	}
	/** 请求用户信息 */
	private void requsetUserInfo(int user_id) {
		((HomeActivity) context).showProgress();
		iUserInfoModel.getOtherUserInfo(user_id, new OnNetResponseCallback() {
			@Override
			public void onSuccess(Object data) {
				((HomeActivity) context).hideProgress();
				if (null != data) {
					initUserInfo((UserInfoEntity) data);
				}
			}
			@Override
			public void onError(int errorCode, String message) {
				((HomeActivity) context).showToast(R.string.request_failed);
				dismiss();
			}
		});
	}
	/** 请求用户技能信息 */
	private void requestUserSkill(int user_id,String goods_info_ids) {
		((HomeActivity) context).showProgress();
		iUserInfoModel.getSkillList(0,user_id, goods_info_ids, new OnNetResponseCallback() {
			@Override
			public void onSuccess(Object data) {
				((HomeActivity) context).hideProgress();
				if (null != data && data instanceof List<?>) {
					initSkills((List<GoodsDetailEntity>) data);
				}
			}
			@Override
			public void onError(int errorCode, String message) {
				((HomeActivity) context).hideProgress();
				initSkills(null);
			}
		});
	}
	/** 请求用户需求信息 */
	private void requestUserNeed(int user_id, String goods_info_ids) {
		((HomeActivity) context).showProgress();
		iUserInfoModel.getNeedList(0, user_id, goods_info_ids, new OnNetResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object data) {
				((HomeActivity) context).hideProgress();
				if (null != data && data instanceof MyNeedEntity) {
					initNeeds(((MyNeedEntity) data).getList());
				}
			}
			@Override
			public void onError(int errorCode, String message) {
				((HomeActivity) context).hideProgress();
				initNeeds(null);
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_close:
			case R.id.line_map_pop:
				dismiss();
				break;
			case R.id.line_user_info_layout:
				Bundle bundle = new Bundle();
				bundle.putInt(UserDetailsActivity.KEY, id);
				UserDetailsActivity.jumpIn(context, bundle);
				dismiss();
				break;
			default:
				break;
		}
	}
	@Override
	public void onHaveATalkClick(GoodsDetailEntity detailEntity) {
		// 跳转聊一聊，窗体消失
		int type=-1;
		switch (loginStatus) {
			case MyApplication.LOG_STATUS_BUYER:
				type=ChatPageEntity.GOOD_TYPE_SKILL;
				break;
			case MyApplication.LOG_STATUS_SELLER:
				type=ChatPageEntity.GOOD_TYPE_NEED;
				requestUserNeed(id, goods_info_ids);
				break;
		}
		ChatPageEntity chatPageEntity = new ChatPageEntity(id,detailEntity,type);
		ChatActivity.haveATalk(context, chatPageEntity);
		dismiss();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle bundle = new Bundle();
		switch (loginStatus) {
			case MyApplication.LOG_STATUS_BUYER:
				bundle.putInt(SkillDetailsActivity.KEY, (int) arg3);
				SkillDetailsActivity.jumpIn(context, bundle);
				break;
			case MyApplication.LOG_STATUS_SELLER:
				bundle.putInt(NeedDetailsActivity.KEY, (int) arg3);
				NeedDetailsActivity.jumpIn(context, bundle);
				break;
		}
		dismiss();
	}
}