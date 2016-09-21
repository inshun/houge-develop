package com.ysp.houge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.SkillMoneyUnitEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/7/29
 * Email: m13478943650@163.com
 */
public class SkillMoneyUnitBaseAdapter extends BaseAdapter {
    private Context context;
    private List<SkillMoneyUnitEntity> entities;

    public SkillMoneyUnitBaseAdapter(Context context, List<SkillMoneyUnitEntity> entities) {
        this.entities = new ArrayList<SkillMoneyUnitEntity>();

        this.context = context;
        this.entities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return entities == null ? 0 : entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_money_unit_listview,parent,false);
            hodler = new Hodler(convertView);
            convertView.setTag(hodler);

        }else {
            hodler = (Hodler) convertView.getTag();
        }

        if (hodler != null){
            hodler.textView.setText(entities.get(position).getName());
        }

        return convertView;
    }


    class Hodler{
        TextView textView;

        public Hodler(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.tv_money_unit);
        }
    }
}
