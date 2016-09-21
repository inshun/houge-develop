package com.ysp.houge.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ysp.houge.R;
import com.ysp.houge.adapter.ViewPagerImgAdapter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.widget.EmoDotView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WT on 2016/7/6.
 */
public class SplashGuideViewActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    //登录、注册模块
    private Button login, register;
    //pager模块
    private RelativeLayout vpLayout;
    private EmoDotView emoDotView;
    private ViewPager pager;

    private int[] imgs={R.drawable.icon_splash_one, R.drawable.icon_splash_two, R.drawable.icon_splash_three, R.drawable.icon_splash_four};
    private ArrayList<ImageView> ivs;
    private int pos;

    public static void jumpIn(Context contex, Bundle bundle) {
        Intent intent = new Intent(contex , SplashGuideViewActivity.class);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        contex.startActivity(intent);
    }


    @Override
    protected void setContentView() {
        //关闭软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_two);
        check();
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {

        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        vpLayout = (RelativeLayout) findViewById(R.id.rela_vp_layout);
        emoDotView = (EmoDotView) findViewById(R.id.mEmoDotView);
        pager = (ViewPager) findViewById(R.id.vp_skill_imgs);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
        emoDotView.setColor(getResources().getColor(R.color.color_e5e5e5),
                getResources().getColor(R.color.color_app_theme_orange_bg));
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

        ivs = new ArrayList<ImageView>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv=new ImageView(this);
//            if(i == 0){
//                iv.setImageResource(imgs[imgs.length - 1]);
//            }else
//            if(i == imgs.length - 1){
//                iv.setImageResource(imgs[0]);
//            }else{
//                iv.setImageResource(imgs[i - 1]);
                iv.setImageResource(imgs[i]);
//            }
            ivs.add(iv);
        }
        vpLayout.setVisibility(View.VISIBLE);
        emoDotView.setPos(ivs.size(), 0);
        pager.setAdapter(new ViewPagerImgAdapter(ivs));

        pager.postDelayed(new MyRunable(), 10000);

    }

    private Handler handler = new Handler();

    private class MyRunable implements Runnable{

        @Override
        public void run() {
            //定时器发送， pager  跳转
            pager.setCurrentItem(pager.getCurrentItem()+1);
            pager.postDelayed(this, 10000);
        }
    }

    //获取定位权限
    public void check(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    100);
        }else {
            //已赋予过权限
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 允许
            } else {
                //无权限
                showToast("请打开对应的权限，否者会导致App无法正常运行！");
            }
        }
    }

    //滚动的时候
    @Override
    public void onPageSelected(int position) {
        emoDotView.setPos(ivs.size(), position);
        this.pos = position;
//        if(position == 0){
//            pos = ivs.size() - 2;
//        }else
        if(position == ivs.size() - 1){
            pos = 0;
        }
    }

    //状态改变，也叫页面位置改变
    @Override
    public void onPageScrollStateChanged(int position) {
        if(position == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            if(pos == 0 || pos == ivs.size() - 1){
                // setCurrentItem， 有两个参数，
                //第二个默认为true，boolean smoothScroll 。 smoothScroll:平滑的滑动
                pager.setCurrentItem(pos, false);
            }
        }
    }

    //滑动的时候
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                LoginActivity.jumpIn(this);
                break;

            case R.id.btn_register:
                RegisterActivity.jumpIn(this);
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
