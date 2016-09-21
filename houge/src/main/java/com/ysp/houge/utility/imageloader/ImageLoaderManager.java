package com.ysp.houge.utility.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;
import com.ysp.houge.BuildConfig;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.utility.FileUtil;
import com.ysp.houge.utility.SizeUtils;

import java.io.File;

/**
 * @author tyn
 * @version 1.0
 * @描述:图片加载管理类，添加此管理类的目的在于便于扩展
 * @Copyright Copyright (c) 2015
 * @Company .
 * @date 2015年6月26日下午3:44:54
 */
public class ImageLoaderManager {
    private DisplayImageOptions mImageOptions;
    private DisplayImageOptions mImageOptionsCircle;
    private DisplayImageOptions mAvatarOptions;
    private DisplayImageOptions mAvatarOptionsCircle;

    /**
     * @描述
     */
    public ImageLoaderManager(Context context) {
        super();
        initializeImageLoader(context);
    }

    public DisplayImageOptions getmAvatarOptions() {
        return mAvatarOptions;
    }

    /**
     * @param context
     * @throws
     * @描述:
     * @方法名: initializeImageLoader
     * @返回类型 void
     * @创建人 tyn
     * @创建时间 2015年9月25日下午2:28:57
     * @修改人 tyn
     * @修改时间 2015年9月25日下午2:28:57
     * @修改备注
     */
    public void initializeImageLoader(Context context) {
        Context mContext = context.getApplicationContext();
        // 配置缓存sd卡上的路径
        File cacheDir = new File(FileUtil.getImageFolder());
        // imageloader总配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext).threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024).memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(200)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // default
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
        // 加载正常图片的配置
        mImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_loading_image)
                .showImageForEmptyUri(R.drawable.bg_loading_image)
                .showImageOnFail(R.drawable.bg_loading_image)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true).cacheInMemory(true).cacheOnDisk(true)
                .build();
        // 加载带有5像素圆角的图片配置
        mImageOptionsCircle = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.bg_loading_image)
                .showImageForEmptyUri(R.drawable.bg_loading_image)
                .showImageOnFail(R.drawable.bg_loading_image)
                .considerExifParams(true).cacheInMemory(true)
                .cacheOnDisk(false).displayer(new RoundedBitmapDisplayer(5))
                .build();
        // 加载圆形头像的配置
        mAvatarOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_small)
                .showImageForEmptyUri(R.drawable.user_small)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnFail(R.drawable.user_small).cacheInMemory(true)
                .displayer(new CircleBitmapDisplayer()).cacheOnDisk(true)
                .build();
        // 加载带有5像素圆角的配置
        mAvatarOptionsCircle = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_small)
                .showImageForEmptyUri(R.drawable.user_small)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnFail(R.drawable.user_small).cacheInMemory(true)
                .cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(5))
                .build();

        //设置ImageLoader日志状态跟随app debug or release版本
        L.writeDebugLogs(BuildConfig.DEBUG);
        L.writeLogs(BuildConfig.DEBUG);
    }

    /**
     * @param view          imageview
     * @param url           图片的地址
     * @param loadImageType 加载图片的类型
     * @throws
     * @描述:加载图片
     * @方法名: loadNormalImage
     * @返回类型 void
     * @创建人 tyn
     * @创建时间 2015年8月22日下午4:34:25
     * @修改人 tyn
     * @修改时间 2015年8月22日下午4:34:25
     * @修改备注
     */
    public void loadNormalImage(View view, String url,
                                LoadImageType loadImageType) {
        if (view instanceof ImageView) {
            switch (loadImageType) {
                case CirclCornerAvatar:
                    ImageLoader.getInstance().displayImage(url, (ImageView) view,
                            mAvatarOptionsCircle);
                    break;
                case CircleCornerImage:
                    ImageLoader.getInstance().displayImage(url, (ImageView) view,
                            mImageOptionsCircle);
                    break;
                case NormalImage:
                    ImageLoader.getInstance().displayImage(url, (ImageView) view,
                            mImageOptions);
                    break;
                case RoundAvatar:
                    ImageLoader.getInstance().displayImage(url, (ImageView) view,
                            mAvatarOptions);
                    break;

                default:
                    break;
            }
        }
    }

    //这个是要改动需求详情写的， 动态的去获取图片的宽高  SB需求
    public void load(View view, String url) {
        ImageLoader.getInstance().displayImage(url, (ImageView) view,
                mAvatarOptionsCircle, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        int imgHeight = bitmap.getHeight();
                        int imgWidth = bitmap.getWidth();

                        int viewWidth = MyApplication.getInstance().getPhoneInfo().getScreenWidth() - SizeUtils.dip2px(MyApplication.getInstance(), 24);
                        int viewHeight = 0;
                        viewHeight = imgHeight * viewWidth / imgWidth;

                        ViewGroup.LayoutParams lp = view.getLayoutParams();
                        lp.width = viewWidth;
                        lp.height = viewHeight;
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
    }

    public enum LoadImageType {
        /**
         * @字段：NormalImage
         * @功能描述：加载正常图片
         * @创建人：tyn
         * @创建时间：2015年9月25日下午2:31:05
         */
        NormalImage,
        /**
         * @字段：CircleCornerImage
         * @功能描述：加载圆角图片
         * @创建人：tyn
         * @创建时间：2015年9月25日下午2:31:16
         */
        CircleCornerImage,
        /**
         * @字段：CirclCornerAvatar
         * @功能描述：加载圆角头像
         * @创建人：tyn
         * @创建时间：2015年9月25日下午2:31:25
         */
        CirclCornerAvatar,
        /**
         * @字段：RoundAvatar
         * @功能描述：加载圆形头像
         * @创建人：tyn
         * @创建时间：2015年9月25日下午2:31:34
         */
        RoundAvatar
    }
}
