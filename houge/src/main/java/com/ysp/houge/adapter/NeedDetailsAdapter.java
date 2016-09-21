package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.DateUtil;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class NeedDetailsAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
	private OnItemClickListener listener;
	private LayoutInflater mInflater;

	public NeedDetailsAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
	}

	public void setListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		GoodsDetailEntity entity = list.get(position);
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_need_details, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (null != holder && null != entity) {
			// ViewHolder操作

			// 用户 start
			UserInfoEntity infoEntity = entity.userInfo;
			if (null != infoEntity) {
				// 加载默认用户头像
				loadImg(holder.icon, "drawable://" + R.drawable.user_small, LoadImageType.RoundAvatar);
				// 设置默认的用户名
				holder.name.setText("无名氏");
				// 头像背景
				switch (infoEntity.sex) {
				case UserInfoEntity.SEX_MAL:
					holder.iconBg.setImageResource(R.drawable.skill_user_icon_man);

					break;
				case UserInfoEntity.SEX_FEMAL:
					holder.iconBg.setImageResource(R.drawable.skill_user_icon_wuman);
					break;
				default:
					holder.iconBg.setImageResource(R.drawable.skill_user_icon_nosex);
					break;
				}

				// 头像
				if (!TextUtils.isEmpty(entity.userInfo.avatar)) {
					loadImg(holder.icon, entity.userInfo.avatar, LoadImageType.RoundAvatar);
				}

				// 用户名
				if (!TextUtils.isEmpty(entity.userInfo.nick)) {
					holder.name.setText(entity.userInfo.nick);
				}
				// 星级
				if (!TextUtils.isEmpty(String.valueOf(entity.userInfo.star))) {
					holder.rate.setText(String.valueOf(entity.userInfo.star) + " ★");
				}

				// 服务保障
                double serviceSafeguardg = -1;
                try {
                    serviceSafeguardg = Double.parseDouble(entity.userInfo.serviceSafeguardg);
                }catch (Exception e){}
                if (serviceSafeguardg > 0) {
					holder.safeguard.setVisibility(View.VISIBLE);
				} else {
					holder.safeguard.setVisibility(View.GONE);
				}

				// 认证
				if (TextUtils.isEmpty(entity.userInfo.verfied)) {
					holder.auth.setVisibility(View.GONE);
				} else {
					holder.auth.setVisibility(View.VISIBLE);
					if (TextUtils.equals("1", entity.userInfo.verfied)) {
						holder.auth.setText("学生认证");
					} else if (TextUtils.equals("2", entity.userInfo.verfied)) {
						holder.auth.setText("个人认证");
					} else if (TextUtils.equals("3", entity.userInfo.verfied)){
						holder.auth.setText("企业认证");
					}else {
                        holder.auth.setVisibility(View.GONE);
                    }
				}

                // 距离
                holder.distance.setText(entity.distance);
				holder.street.setText(entity.address_detail);
			}
			// 用户 end

			// 需求标题
			holder.title.setText("需要 · 未知标题");
			if (!TextUtils.isEmpty(entity.title)) {
				holder.title.setText("需要 · " + entity.title);
			}

			try {
				// 需求时间(这个地方如果为空就new一个当前时间出来)
				String date = null == entity.start_time ? DateUtil.getCurrentDateTime() : entity.start_time;
				StringBuilder sb = new StringBuilder();
				sb.append("时间 · ");
				// 星期几
				switch (DateUtil.getDiffDays(DateUtil.getCurrentDate(), date)) {
				case 0:
					sb.append("今天");
					break;
				case 1:
					sb.append("明天");
					break;
				case 2:
					sb.append("后天");
					break;
				default:
					sb.append(DateUtil.WEEKS[DateUtil.getDayOfWeek(date.substring(0, 10)) - 1]);
					break;
				}

				// 几点
				sb.append(" ");
				sb.append(date.subSequence(date.indexOf(" "), date.length() - 3));
				sb.append(" ");
				sb.append("开始");

				holder.time.setText(sb.toString());
			} catch (Exception e) {
				holder.time.setText("时间 · 未知时间");
			}

//			// 当面付图标
//			switch (new Random().nextInt(2)) {
//			case 0:
				holder.faceToFace.setVisibility(View.GONE);
//				break;
//			default:
//				holder.faceToFace.setVisibility(View.VISIBLE);
//				break;
//			}

			// 价格
			holder.price.setText("￥" + entity.price);

			// 留言数量
			holder.levelMsg.setText(String.valueOf(entity.comment_count));

			// 赞数量
			holder.praise.setText(String.valueOf(entity.view_count));

			if(entity.is_system == 1){
				holder.haveATalk.setVisibility(View.GONE);
			}
			// 点击事件
			final int index = position;
			holder.haveATalk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK);
					}
				}
			});

			holder.share.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_SHARE);
					}
				}
			});

			holder.need.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_NEED_DETAIL);
					}
				}
			});

			holder.userInfo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_USER_DETAIL);
					}
				}
			});
		}
		return convertView;
	}

	@Override
	public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
		if (isRefresh) {
			this.list.clear();
		}
		this.list.addAll(data);
	}

	@Override
	public List<GoodsDetailEntity> getData() {
		return list;
	}

/** 加载图片 */
	private void loadImg(ImageView view, String avatarUrl, LoadImageType type) {
		MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, avatarUrl, type);
	}

		class Holder {
		LinearLayout need; // 需求详情

		ImageView icon; // 头像
		ImageView iconBg; // 头像背景
		TextView name; // 昵称
		TextView rate; // 等级
		TextView safeguard; // 服务保障
		TextView auth; // 认证
		TextView distance; // 距离
		TextView street; //街道
		LinearLayout userInfo; // 用户信息

		TextView title;// 标题
		TextView time;// 单位
		ImageView faceToFace; // 当面付
		TextView price;// 价格

		TextView haveATalk;// 聊一聊

		ImageView share; // 分享
		TextView praise; // 赞的数量
		TextView levelMsg; // 留言数量

		public Holder(View convertView) {
			need = (LinearLayout) convertView.findViewById(R.id.line_need_skill);

			icon = (ImageView) convertView.findViewById(R.id.iv_user_icon);
			iconBg = (ImageView) convertView.findViewById(R.id.iv_user_icon_bg);
			name = (TextView) convertView.findViewById(R.id.tv_user_name);
			rate = (TextView) convertView.findViewById(R.id.tv_rate);
			safeguard = (TextView) convertView.findViewById(R.id.tv_service_safeguard);
			auth = (TextView) convertView.findViewById(R.id.tv_auth);
			distance = (TextView) convertView.findViewById(R.id.tv_distance);
			street = (TextView) convertView.findViewById(R.id.tv_street);
			userInfo = (LinearLayout) convertView.findViewById(R.id.line_user_info_layout);

			title = (TextView) convertView.findViewById(R.id.tv_good_title);
			time = (TextView) convertView.findViewById(R.id.tv_good_time);
			faceToFace = (ImageView) convertView.findViewById(R.id.iv_face_to_face);
			price = (TextView) convertView.findViewById(R.id.tv_goods_price);

			haveATalk = (TextView) convertView.findViewById(R.id.tv_have_a_talk);

			share = (ImageView) convertView.findViewById(R.id.iv_share);
			praise = (TextView) convertView.findViewById(R.id.tv_praise);
			levelMsg = (TextView) convertView.findViewById(R.id.tv_msg);
		}
	};
}
