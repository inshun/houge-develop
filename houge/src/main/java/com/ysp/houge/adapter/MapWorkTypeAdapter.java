package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by it_huangxin on 2016/1/29.
 */
public class MapWorkTypeAdapter extends BaseAdapter {
    private Context context;
    private int index = 0;
    private List<WorkTypeEntity> list = new ArrayList<WorkTypeEntity>();

    public MapWorkTypeAdapter(Context context, List<WorkTypeEntity> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<WorkTypeEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;

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
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        // 获得操作对象
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_map_work_type, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        WorkTypeEntity workTypeEntity = list.get(position);
        if(null != holder && null != workTypeEntity){
            holder.workType.setText(workTypeEntity.getName());
            holder.indicator.setVisibility(View.GONE);

            //对应下标显示指示器
            if (index == position){
                holder.indicator.setVisibility(View.VISIBLE);
            }

            switch (MyApplication.getInstance().getLoginStaus()){
                case MyApplication.LOG_STATUS_BUYER:
                    holder.workType.setTextColor(context.getResources().getColor(R.color.color_app_theme_orange_bg));
                    holder.indicator.setBackgroundResource(R.color.color_app_theme_orange_bg);
                    break;
                case MyApplication.LOG_STATUS_SELLER:
                    holder.workType.setTextColor(context.getResources().getColor(R.color.color_app_theme_blue_bg));
                    holder.indicator.setBackgroundResource(R.color.color_app_theme_blue_bg);
                    break;
            }
        }
        return convertView;
    }

    @SuppressLint("CutPasteId")
    class Holder {
        TextView workType;
        View indicator;

        public Holder(View convertView) {
            workType = (TextView) convertView.findViewById(R.id.tv_work_type);
            indicator = convertView.findViewById(R.id.v_indicator);
        }
    }
}
