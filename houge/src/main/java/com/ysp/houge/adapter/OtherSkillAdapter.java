package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class OtherSkillAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
    private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
    private LayoutInflater mInflater;
    private OrderDetailsEntity orderDetailsEntity;

    public OtherSkillAdapter(Context context) {
        super();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ? 1 : list.size();
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
        if (convertView == null) {
            convertView = (View) mInflater.inflate(R.layout.item_other_skill, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (list.size() == 0) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
            convertView.setLayoutParams(params);
        }

        if (position == 0 && list.size() != 0) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(params);
        }

        if (null != holder && !list.isEmpty()) {
            GoodsDetailEntity entity = list.get(position);
            if (null != entity) {
                // 图片
                if (null != entity.image && entity.image.size() > 0) {
                    MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.img, entity.image.get(0),
                            LoadImageType.NormalImage);
                }

                // 名称
                switch (MyApplication.getInstance().getLoginStaus()){
                    case  MyApplication.LOG_STATUS_BUYER:
                        holder.title.setText("我能 · " + entity.title);
                        break;
                    case  MyApplication.LOG_STATUS_SELLER:
                        holder.title.setText("需要 · " + entity.title);
                        break;
                }

//                // 价格单位拼接
//                if (TextUtils.isEmpty(entity.price)) {
//                    holder.unit.setText("免薪实习");
//                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(entity.price);
                    if (!TextUtils.isEmpty(entity.unit)) {
                        sb.append(entity.unit);

                    }
                    // 显示售价以及单位
                    if (sb.length() > 0) {
                        holder.unit.setText(sb.toString());
//                    } else {
//                        holder.unit.setText("无售价");
//                    }
                }

                holder.orderCount.setText("已预约" + entity.order_count + "次");
            }
        }
        return convertView;
    }

    @Override
    public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
        list.addAll(data);
    }

    @Override
    public List<GoodsDetailEntity> getData() {
        return list;
    }

    class Holder {
        ImageView img;
        TextView title;
        TextView unit;
        TextView orderCount;

        public Holder(View convertView) {
            img = (ImageView) convertView.findViewById(R.id.iv_skill_img);
            title = (TextView) convertView.findViewById(R.id.tv_skill_description);
            unit = (TextView) convertView.findViewById(R.id.tv_skill_price_and_unit);
            orderCount = (TextView) convertView.findViewById(R.id.tv_skill_order_count);
        }
    }

}
