package com.ysp.houge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WT on 2016/7/1.
 */
public class NeedDetailsImageAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>>{
    private final LayoutInflater mInflater;
    private List<GoodsDetailEntity> lists = new ArrayList<GoodsDetailEntity>();
    private  GoodsDetailEntity list = new GoodsDetailEntity();

    public NeedDetailsImageAdapter(Context context, GoodsDetailEntity entity) {
        super();
        mInflater = LayoutInflater.from(context);
        list = entity;
    }

    @Override
    public int getCount() {
        return list.image.size();
    }

    @Override
    public Object getItem(int position) {
        return list.image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_need_details_image,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }


        for (int i = 0; i < list.image.size(); i++) {
            if(i == 0){
                loadImg(holder.one, list.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
            }
            if(i == 1){
                loadImg(holder.two, list.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
            }
            if(i == 2){
                loadImg(holder.three, list.image.get(i), ImageLoaderManager.LoadImageType.NormalImage);
            }

            LogUtil.setLogWithE("AA", i+"---------LOG");
        }

        holder.one.setTag(list.image);


        return convertView;
    }

    private void loadImg(ImageView view, String avatarUrl, ImageLoaderManager.LoadImageType type) {
        MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, avatarUrl, type);
    }

    @Override
    public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
//        if (isRefresh) {
//            list.clear();
//        }
//        list.addAll(data);
    }

    @Override
    public List<GoodsDetailEntity> getData() {
        return null                                                                                                                                                                                                                                                                                                                                                    ;
    }


    class Holder{

         ImageView one;
         ImageView two;
         ImageView three;

        public Holder(View convertView) {
            one = (ImageView) convertView.findViewById(R.id.iv_need_detials_images_one);
            two = (ImageView) convertView.findViewById(R.id.iv_need_detials_images_two);
            three = (ImageView) convertView.findViewById(R.id.iv_need_detials_images_three);
        }

    }


}
