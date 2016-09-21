package com.ysp.houge.utility.share;

import android.app.Activity;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.ShareEntity;
import com.ysp.houge.utility.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 友盟分享工具类
 *
 * @ClassName: ShareUtils
 * @author: hx
 * @date: 2016年1月7日 上午11:26:06
 * <p/>
 * 版本: 1.0
 */
public class ShareUtils {

    private static int id;
    private static int user_id;
    private static ShareEntity shareEntity;
    private static int qufen;

    //skillOrNeed  用来区分技能还是需求的url，1 or 2 ，  0 是用户详情。
    public static void share(Activity activity, GoodsDetailEntity detailEntity, int skillOrNeed){
        id = detailEntity.id;
        user_id = detailEntity.user_id;
         qufen = skillOrNeed;

        if (null==detailEntity)
            return;
        else {
            try{
                List<String> image = new ArrayList<>();
                if (null != detailEntity.image && !detailEntity.image.isEmpty())
                    image.add(detailEntity.image.get(0));
                switch (qufen){
                    case 1:
                        //技能
                        shareEntity = new ShareEntity(activity, detailEntity.desc,detailEntity.title,image,"http://api.jiehuolou.com/h5/skill/user_id/"+user_id+"/id/"+id+"");
                        break;
                    case 2:
                        //需求
                        shareEntity = new ShareEntity(activity, detailEntity.desc,detailEntity.title,image,"http://api.jiehuolou.com/h5/need/user_id/"+user_id+"/id/"+id+"");

                        break;
                    case 0:
                        //用户
                        shareEntity = new ShareEntity(activity, detailEntity.desc,detailEntity.title,image,"http://www.jiehuolou.com");
                        break;
                }
                ShareUtils.creteShare(shareEntity);
            }catch (Exception e){
            }
        }

    }

    public static void creteShare(ShareEntity shareEntity) {
        if (null == shareEntity) {
            LogUtil.setLogWithE("ShareUtils creteShare", "a null share entity");
            return;
        } else if (null == shareEntity.getActivity()) {
            LogUtil.setLogWithE("ShareUtils creteShare", "a null share activity");
            return;
        } else if (TextUtils.isEmpty(shareEntity.getText())) {
            LogUtil.setLogWithE("ShareUtils creteShare", "a null share text");
            return;
        }else if (TextUtils.isEmpty(shareEntity.getTitle())){
            LogUtil.setLogWithE("ShareUtils creteShare", "a null share title");
            return;
        }else if (TextUtils.isEmpty(shareEntity.getUrl())){
            LogUtil.setLogWithE("ShareUtils creteShare", "a null share url");
            return;
        }else{
           try{
               ShareAction shareAction = new ShareAction(shareEntity.getActivity());
               shareAction.setDisplayList(ShareEntity.displaylist);
               shareAction.withText(shareEntity.getText());
               shareAction.withTitle(shareEntity.getTitle());
               switch (qufen){
                   case 1:
                       //技能
                       shareAction.withTargetUrl("http://api.jiehuolou.com/h5/skill/user_id/"+user_id+"/id/"+id+"");
                       break;
                   case 2:
                       //需求
                       shareAction.withTargetUrl("http://api.jiehuolou.com/h5/need/user_id/"+user_id+"/id/"+id+"");
                       break;
                   case 0:
                       //用户
                       shareAction.withTargetUrl("http://www.jiehuolou.com");
                       break;
               }
               if (null != shareEntity.getImage() && !shareEntity.getImage().isEmpty()){
                   UMImage umImage = new UMImage(shareEntity.getActivity().getApplicationContext(),shareEntity.getImage().get(0));
                   shareAction.withMedia(umImage);
               }
               shareAction.open();
           }catch (Exception e){
               LogUtil.setLogWithE("ShareUtils creteShare",e.getMessage() == null ? "a null message" : e.getMessage());
           }
        }
    }
}
