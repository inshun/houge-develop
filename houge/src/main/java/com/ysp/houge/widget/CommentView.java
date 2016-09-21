package com.ysp.houge.widget;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.List;
@SuppressLint("CutPasteId")
public class CommentView extends LinearLayout implements OnClickListener {
	private Context context;
	private int size;
	private int loveCount, commentCount;
	private TextView tvLoveCount;
	private LinearLayout loveLayout;
	private ImageView avatars[];
	private ImageView loveSeven;
	private TextView tvCommentCount;
	private ClearEditText comment;
	private Button levelMsg;
	private Comment comments[];
	private TextView moreCommet;
	private TextView more;
	private CommentViewListener listener;
	public CommentView(Context context) {
		super(context);
	}
	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs);
		init(context);
	}
	public void setListener(CommentViewListener listener) {
		this.listener = listener;
	}
	private void init(Context context) {
		this.context = context;
		// 加载布局
		View.inflate(context, R.layout.view_comment, this);
		initView();
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void initView() {
		// TODO 初始化视图
		tvLoveCount = (TextView) findViewById(R.id.tv_browse_count);
		loveLayout = (LinearLayout) findViewById(R.id.line_love_layout);
		ImageView avatarOne = (ImageView) findViewById(R.id.iv_browse_one);
		ImageView avatarTwo = (ImageView) findViewById(R.id.iv_browse_two);
		ImageView avatarThree = (ImageView) findViewById(R.id.iv_browse_three);
		ImageView avatarFour = (ImageView) findViewById(R.id.iv_browse_four);
		ImageView avatarFive = (ImageView) findViewById(R.id.iv_browse_five);
		ImageView avatarSix = (ImageView) findViewById(R.id.iv_browse_six);
		avatars = new ImageView[] {avatarOne ,avatarTwo, avatarThree,avatarFour,avatarFive,avatarSix};
		loveSeven = (ImageView) findViewById(R.id.iv_browse_seven);
		loveSeven.setOnClickListener(this);
		tvCommentCount = (TextView) findViewById(R.id.tv_message_count);
		comment = (ClearEditText) findViewById(R.id.et_message);
		levelMsg = (Button) findViewById(R.id.btn_message_publish);
		levelMsg.setOnClickListener(this);
		comments = new Comment[] {
				new Comment((RelativeLayout) findViewById(R.id.rela_msg_one),
						(ImageView) findViewById(R.id.iv_leavel_one), (TextView) findViewById(R.id.tv_leavel_one)),
				new Comment((RelativeLayout) findViewById(R.id.rela_msg_two),
						(ImageView) findViewById(R.id.iv_leavel_two), (TextView) findViewById(R.id.tv_leavel_two)),
				new Comment((RelativeLayout) findViewById(R.id.rela_msg_three),
						(ImageView) findViewById(R.id.iv_leavel_three),
						(TextView) findViewById(R.id.tv_leavel_three)) };
		moreCommet = (TextView) findViewById(R.id.tv_more_message);
		moreCommet.setOnClickListener(this);
		more = (TextView) findViewById(R.id.tv_more);
		// 根据进入身份不同显示不同的评论背景、底部文字
		int loginStatus = MyApplication.getInstance().getLoginStaus();
		switch (loginStatus) {
			case MyApplication.LOG_STATUS_BUYER:
				levelMsg.setBackground(getResources().getDrawable(R.drawable.shapa_orange_radius));
				more.setText(getResources().getString(R.string.other_skill));
				break;
			case MyApplication.LOG_STATUS_SELLER:
				levelMsg.setBackgroundResource(R.drawable.shapa_blue_radius);
				more.setText(getResources().getString(R.string.other_need));
				break;
		}
	}
	/** 设置赞的数据 */
	public void setLove(List<UserInfoEntity> list) {
		if (null != list && !list.isEmpty()) {
			loveCount = list.size();
			loveLayout.setVisibility(VISIBLE);
			// 获得屏幕宽度
			int screenWidth = SizeUtils.getScreenWidth((Activity) context);
			int count = 0;
			if (loveCount < 7) {
				count = 6;
			} else {
				count = 7;
			}
			size = (screenWidth - SizeUtils.dip2px(context, 12) * 2 - (count - 1) * SizeUtils.dip2px(context, 8)) / count;
			for (int i = 0; i < avatars.length; i++) {
				//设置图片宽高度
				avatars[i].getLayoutParams().width = size;
				avatars[i].getLayoutParams().height = size;
			}
		}else {
			loveCount = 0;
			loveLayout.setVisibility(GONE);
			return;
		}
		if (loveCount <= 0) {
			tvLoveCount.setText("快来当第一个赞的人吧");
			return;
		} else if (loveCount <= 6) {
			tvLoveCount.setText("快来抢占前排宝座");
			// 数量小于等于6的时候隐藏更多
			loveSeven.setVisibility(View.GONE);
		} else {
			//处理喜欢的人数过多的时候
			if (loveCount > 1000) {
				tvLoveCount.setText("999+个人喜欢");
			} else if (loveCount > 100) {
				tvLoveCount.setText("99+个人喜欢");
			} else {
				tvLoveCount.setText(loveCount + "个人喜欢");
			}
			loveSeven.getLayoutParams().width = size;
			loveSeven.getLayoutParams().height = size;
			loveSeven.setVisibility(VISIBLE);
		}
		if (loveCount <= 0)
			return;
		//循环显示前6个喜欢的人
		for (int i = 0; i < 6; i++) {
			if (loveCount > i) {
				avatars[i].setVisibility(View.VISIBLE);
				// 头像
				String avatar = list.get(i).avatar;
				if (TextUtils.isEmpty(avatar)) {
					avatars[i].setImageResource(R.drawable.user_small);
				} else {
					MyApplication.getInstance().getImageLoaderManager().loadNormalImage(avatars[i], avatar,
							LoadImageType.RoundAvatar);
				}
				// 头像背景
				int sex = list.get(i).sex;
				switch (sex) {
					case UserInfoEntity.SEX_FEMAL:
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							avatars[i].setBackground(getContext().getResources().getDrawable(R.drawable.shapa_sex_femal));
						}
						break;
					case UserInfoEntity.SEX_MAL:
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							avatars[i].setBackground(getContext().getResources().getDrawable(R.drawable.shapa_sex_mal));
						}
						break;
					default:
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							avatars[i].setBackground(getContext().getResources().getDrawable(R.drawable.shapa_sex_def));
						}
						break;
				}
				//点击头像，进入用户详情页
				avatars[i].setTag(String.valueOf(list.get(i).id));
				avatars[i].setOnClickListener(this);
			} else {
				avatars[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void setComment(List<CommentEntity> list) {
		if (null != list && !list.isEmpty()) {
			commentCount = list.size();
			if (commentCount > 3 )
				moreCommet.setVisibility(View.VISIBLE);
			else
				moreCommet.setVisibility(GONE);
		}else {
			commentCount = 0;
			moreCommet.setVisibility(GONE);
		}
		if (commentCount <= 0) {
			tvCommentCount.setText("快来评论吧");
			// 隐藏三条评论以及更多评论
			comments[0].layout.setVisibility(View.GONE);
			comments[1].layout.setVisibility(View.GONE);
			comments[2].layout.setVisibility(View.GONE);
		} else {
			if (commentCount > 1000) {
				tvCommentCount.setText("999+个人评论");
			} else if (commentCount > 100) {
				tvCommentCount.setText("99+个人评论");
			} else {
				tvCommentCount.setText(commentCount + "个人评论");
			}
			//显示前三条评论
			for (int i = 0; i < 3; i++) {
				if (list.size() > i) {
					comments[i].layout.setVisibility(View.VISIBLE);
					// 头像边框
					switch (list.get(i).sex) {
						case UserInfoEntity.SEX_MAL:
							comments[0].avatar
									.setBackground(getContext().getResources().getDrawable(R.drawable.shapa_sex_mal));
							break;
						case UserInfoEntity.SEX_FEMAL:
							comments[0].avatar
									.setBackground(getContext().getResources().getDrawable(R.drawable.shapa_sex_femal));
							break;
						default:
							comments[0].avatar
									.setBackground(getContext().getResources().getDrawable(R.drawable.shapa_sex_def));
							break;
					}
					// 头像
					MyApplication.getInstance().getImageLoaderManager().loadNormalImage(comments[i].avatar,
							list.get(i).avatar, LoadImageType.RoundAvatar);
					comments[i].comment.setText(list.get(i).content);
					comments[i].layout.setOnClickListener(this);
				} else {
					comments[i].layout.setVisibility(View.GONE);
				}
			}
		}
		// 如果评论数量大于3的话显示更多
		if (commentCount <= 3) {
			moreCommet.setVisibility(View.GONE);
		}
	}
	public void hasOtherSkill(boolean hasOtherSkill) {
		if (hasOtherSkill) {
			more.setVisibility(View.VISIBLE);
		} else {
			more.setVisibility(View.GONE);
		}
	}



	@Override
	public void onClick(View v) {
		if (null == listener) {
			return;
		}
		switch (v.getId()) {
			// 更多喜欢
			case R.id.iv_browse_seven:
				listener.onMoreLoveClick();
				break;
			// 评论
			case R.id.btn_message_publish:
				listener.onLevelMessage(comment.getText().toString());
				comment.setText("");
				clearFocus();
				break;
			// 更多评论
			case R.id.rela_msg_one:
			case R.id.rela_msg_two:
			case R.id.rela_msg_three:
			case R.id.tv_more_message:
				listener.onMoreCommentClick();
				clearFocus();
				break;
			default:
				clearFocus();
				break;
		}

		if (null != v.getTag() && v.getTag() instanceof String){
			try {
				//用户中心
				Bundle bundle = new Bundle();
				bundle.putInt(UserDetailsActivity.KEY,Integer.parseInt(v.getTag().toString()));
				UserDetailsActivity.jumpIn(context,bundle);
			}catch (Exception e){}
		}
	}
	/**
	 * 描述： 留言监听器
	 *
	 * @ClassName: OnLevelMessageListener
	 *
	 * @author: hx
	 *
	 * @date: 2015年12月14日 下午12:51:32
	 *
	 *        版本: 1.0
	 */
	public interface CommentViewListener {
		/** 更多喜欢 */
		void onMoreLoveClick();
		/** 留言 */
		void onLevelMessage(String message);
		/** 更多留言 */
		void onMoreCommentClick();
	}
	class Comment {
		RelativeLayout layout;
		ImageView avatar;
		TextView comment;
		public Comment(RelativeLayout layout, ImageView avatar, TextView comment) {
			super();
			this.layout = layout;
			this.avatar = avatar;
			this.comment = comment;
		}
	}
}