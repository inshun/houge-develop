package com.ysp.houge.popwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillMoneyUnitBaseAdapter;
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
 * @date 2016/8/1
 * Email: m13478943650@163.com
 */
public class SkillMoneyUnitPopupWindow extends BasePopupWindow {
    private Context context;
    private List<SkillMoneyUnitEntity> entities=new ArrayList<SkillMoneyUnitEntity>();
    private ListView listView;
    private SkillMoneyUnitBaseAdapter adapter;
    private EditText editText;

    public SkillMoneyUnitPopupWindow(Context context, List<SkillMoneyUnitEntity> entities, EditText editText) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_money_util, null)
                , ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;
        this.entities = entities;
        this.editText = editText;
    }

    public SkillMoneyUnitPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    @Override
    public void initViews() {
        Log.d("SkillMoneyUnitPopupWind", "initview");
        listView = (ListView) contentView.findViewById(R.id.lv_money_unit);

    }

    @Override
    public void initEvents() {
        Log.d("SkillMoneyUnitPopupWind", "initEvents");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(entities.get(position).getName());
                SkillMoneyUnitPopupWindow.this.dismiss();
            }
        });
    }


    @Override
    public void initData() {
        Log.d("SkillMoneyUnitPopupWind", "initData");
        adapter = new SkillMoneyUnitBaseAdapter(context, entities);
        listView.setAdapter(adapter);

    }

    public void showAsDroup(View parent) {
        Log.d("SkillMoneyUnitPopupWind", "showAsDroup");
        this.showAsDropDown(parent);
    }
}
