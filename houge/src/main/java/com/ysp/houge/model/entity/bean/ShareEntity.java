package com.ysp.houge.model.entity.bean;

//import com.umeng.socialize.UMShareListener;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： 分享实体
 *
 * @ClassName: ShareEntity
 * 
 * @author: hx
 * 
 * @date: 2016年1月7日 上午11:11:13
 * 
 *        版本: 1.0
 */
public class ShareEntity implements Serializable{
    public static final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
            {
//                    SHARE_MEDIA.SINA,
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
            };

    private Activity activity;

    private String text;

    private String title;

    private List<String> image;

    private String url;

    private int skill;

    private int userId;

    public ShareEntity(Activity activity, String text, String title, List<String> image, String url) {
        this.activity = activity;
        this.text = text;
        this.title = title;
        this.image = image;
        this.url = url;

    }

    public Activity getActivity() {
        return activity;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public int getSkill(){
    return skill;}

    public int getUserId(){
        return userId;
    }
}