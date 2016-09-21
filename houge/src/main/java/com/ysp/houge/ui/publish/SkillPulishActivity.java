package com.ysp.houge.ui.publish;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SkillMoneyUnitBaseAdapter;
import com.ysp.houge.adapter.SkillPulishImgAdapter;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.model.entity.bean.SkillMoneyUnitEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.presenter.ISkillPulishPresenter;
import com.ysp.houge.presenter.impl.SkillPulishPresenter;
import com.ysp.houge.ui.HomeActivity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ISkillPulishPagerView;
import com.ysp.houge.ui.map.ResidentAddressActivity;
import com.ysp.houge.ui.me.TimeManagerActivity;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.NoScrollGridView;
import com.ysp.houge.widget.image.selector.MultiImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hx
 * @version 1.0
 * @描述: 卖家发布页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2015年11月2日下午3:30:47
 */
public class
SkillPulishActivity extends BaseFragmentActivity
        implements ISkillPulishPagerView, OnClickListener, OnItemClickListener, OnItemLongClickListener, View.OnTouchListener {
    public static final String KEY = "skill_id";
    /*
    *技能发布页面的根布局*/
    private LinearLayout root;
    private int keyBoardHeight;
    private int[] location = new int[2];

    /**
     * 技能标题
     */
    private EditText mSkillTitle;
    /**
     * 技能描述
     */
    private EditText mSkillDesc;
    /**
     * 技能价格
     */
    private EditText mSkillPrice;
    /**
     * 技能单位
     */
    private EditText mSkillUnit;
    /**
     * 免薪实习布局
     */
    private RelativeLayout mLayoutSkillFree;
    /**
     * 免薪实习图片
     */
    private ImageView mSkillFree;
    /**
     * 免薪提供的布局
     */
    private RelativeLayout mLayoutSkillFreeTreatment;
    /**
     * 工作餐
     */
    private TextView mWorkingLunch;
    /**
     * 住宿
     */
    private TextView mStay;
    /**
     * 提交
     */
    private TextView mSubmit;
    /**
     * 收起
     */
    private ImageView mPickUp;

    /**
     * 免薪实习选中、未选中图片数组
     */
    private int[] isFreeImgs = new int[]{R.drawable.icon_sel_blue, R.drawable.icon_def};
    private SkillPulishImgAdapter adapter;
    /**
     * 技能图片网格
     */
    private NoScrollGridView nsgvImgs;

    private ISkillPulishPresenter iSkillPulishPresenter;
    private Bundle tiaojian;
    private Bundle bundle;
    private ProgressDialog dialog;
    /*付款单位*/
    private PopupWindow popManeyUtil;
    private ListView lvMoneyUnit;
    private SkillMoneyUnitBaseAdapter moneyUnitBaseAdapter;
    private List<SkillMoneyUnitEntity> moneyUnitEntitys;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;

    public static void JumpIn(Context context, Bundle bundle) {
        Intent intent = new Intent(context, SkillPulishActivity.class);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_pulish);
    }

    @Override
    protected void initActionbar() {
        MyActionBar actionBar = new MyActionBar(this);
        tiaojian = getIntent().getExtras();
        if (tiaojian != null) {
            if (tiaojian != null) {
                actionBar.setLeftEnable(true);
                actionBar.setLeftIcon(R.drawable.icon_canle_push_need);
                actionBar.setLeftClickListenner(this);
                actionBar.setRightText("下一步");
                actionBar.setRightClickListenner(this);
            }
        }
        actionBar.setTitle(getString(R.string.pulish_skill));
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);

    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        root = (LinearLayout) findViewById(R.id.root);
        /*添加照片*/
        nsgvImgs = (NoScrollGridView) findViewById(R.id.nsgv_seller_pulish_imgs);
        nsgvImgs.setOnItemClickListener(this);
        nsgvImgs.setOnItemLongClickListener(this);

        /*技能标题 && 技能描述*/
        mSkillTitle = (EditText) findViewById(R.id.et_seller_title);
        mSkillDesc = (EditText) findViewById(R.id.et_seller_desc);

        /*技能价格 && 价格单位*/
        mSkillPrice = (EditText) findViewById(R.id.et_seller_price);
        mSkillUnit = (EditText) findViewById(R.id.et_seller_unit);
        mSkillUnit.setOnTouchListener(this);
        mSkillPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                controlKeyBoardLayout(root, mSkillPrice, true);
            }
        });

        mLayoutSkillFree = (RelativeLayout) findViewById(R.id.rela_free_practice);
        mSkillFree = (ImageView) findViewById(R.id.iv_free_practice);

        mLayoutSkillFree.setOnClickListener(this);

        mLayoutSkillFreeTreatment = (RelativeLayout) findViewById(R.id.rela_free_practice_treatment);
        mWorkingLunch = (TextView) findViewById(R.id.tv_working_lunch);
        mStay = (TextView) findViewById(R.id.tv_stay);

        mWorkingLunch.setOnClickListener(this);
        mStay.setOnClickListener(this);

        /*提交*/
        mSubmit = (TextView) findViewById(R.id.tv_submit);
        mSubmit.setOnClickListener(this);

        /*关闭*/
        mPickUp = (ImageView) findViewById(R.id.iv_pick_up);
        mPickUp.setOnClickListener(this);

        tiaojian = getIntent().getExtras();
        if (tiaojian != null) {
            if (tiaojian.getInt("TAG") == 2) {
                mPickUp.setVisibility(View.INVISIBLE);
                mSubmit.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        iSkillPulishPresenter = new SkillPulishPresenter(this);
        adapter = new SkillPulishImgAdapter(this);
        nsgvImgs.setAdapter(adapter);
        /*初始化技能单位popup*/
        moneyUnitEntitys = new ArrayList<SkillMoneyUnitEntity>();
        iSkillPulishPresenter.getSkillMoneyUnit();

        if (null != getIntent() && null != getIntent().getExtras() && getIntent().getExtras().containsKey(KEY)) {
            // id = ((GoodsDetailEntity)
            // getIntent().getExtras().getSerializable(KEY)).id;
            // ==========================================================================
            // 或得到需要编辑的技能ID
        } else {
            // 初始化图片网格
            iSkillPulishPresenter.choosePicBack("233");
            iSkillPulishPresenter.getSetTime();
            iSkillPulishPresenter.getSetAddress();
        }
        // 初始化免薪实习状态
        iSkillPulishPresenter.changeFreeStatus();

    }


    private void controlKeyBoardLayout(final LinearLayout root, final EditText mSubmit, final boolean isScroll) {
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                root.getWindowVisibleDisplayFrame(rect);
                keyBoardHeight = root.getRootView().getHeight() - rect.bottom;
                if (keyBoardHeight > 100 && isScroll == true) {
                      /*调整屏幕位置*/
                    //获取scrollToView在窗体的坐标
                    mSubmit.getLocationInWindow(location);

                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + mSubmit.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);

                } else {
                    root.scrollTo(0, 0);
                }
            }
        };
        root.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && globalLayoutListener != null) {
            root.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        }
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 接收时间页面的数据
     */
    public void onEventMainThread(String string) {
        iSkillPulishPresenter.setTimeFinish();
        iSkillPulishPresenter.submit(mSkillTitle.getText().toString().trim(),
                mSkillDesc.getText().toString().trim(), mSkillPrice.getText().toString().trim(),
                mSkillUnit.getText().toString().trim());
    }

    /**
     * 接收地图页面的数据
     */
    public void onEventMainThread(LocationChooseEventBusEntity locationEntity) {
        iSkillPulishPresenter.addAddress(locationEntity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (tiaojian != null) {
                    if (tiaojian.getInt("TAG") == 2) {
                        HomeActivity.jumpIn(this, null);
                        pickUp();
                        AppManager.getAppManager().removeActivity(this);
                    }
                } else {
                    pickUp();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //        解决软键盘遮挡Edittext的问题
            case R.id.et_seller_price:

                break;
            // 免薪实习
            case R.id.rela_free_practice:
                iSkillPulishPresenter.changeFreeStatus();
                break;

            // 工作餐
            case R.id.tv_working_lunch:
                iSkillPulishPresenter.changeWorkLunchStatus();
                break;

            // 住宿
            case R.id.tv_stay:
                iSkillPulishPresenter.changeStayStatus();
                break;

            // 提交
            case R.id.tv_submit:
                // 下一步（第一次操作）
            case R.id.menu_head_right:
                if (DoubleClickUtil.isFastClick()) {
                    return;
                }
                iSkillPulishPresenter.submit(mSkillTitle.getText().toString().trim(),
                        mSkillDesc.getText().toString().trim(), mSkillPrice.getText().toString().trim(),
                        mSkillUnit.getText().toString().trim());
                break;
            case R.id.menu_head_left:
                if (tiaojian != null) {
                    if (tiaojian.getInt("TAG") == 2) {
                        HomeActivity.jumpIn(this, null);
                        pickUp();
                        AppManager.getAppManager().removeActivity(this);
                    }
                } else {
                    pickUp();
                }
                break;

            // 收起
            case R.id.iv_pick_up:
                iSkillPulishPresenter.pickUp();
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (adapter.getData().size() - 1 == position) {
            // 处理最后一个的问题
            if (position == 8 && !TextUtils.isEmpty(adapter.getData().get(8))) {
                return;
            }
            showAddPicDialog();
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // 处理添加图片问题
        if (!TextUtils.isEmpty(adapter.getData().get(position))) {
            iSkillPulishPresenter.delImage(position);
            showToast("删除成功");
        }

        return true;
    }

    @Override
    public void loadImg(List<String> picList) {
        adapter.setData(picList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showAddPicDialog() {
        check();
    }

    public void check() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            //TODO 这个方法要求API最小是16 目前我们应用要求的最小API是15 所以针对15的用户此权限获取不了
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        } else {
            //已赋予过权限
            // TODO 创建选择图片对话框
            createChoosePicDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 允许
                // TODO 创建选择图片对话框
                createChoosePicDialog();
            } else {
                //无权限
                showToast("请打开对应的权限，否者会导致App无法正常运行！");
            }
        }
    }


    @Override
    public void changeFreeStatus(boolean isFree) {
        // TODO 改变免薪实习状态
        if (isFree) {
            mSkillFree.setBackgroundResource(isFreeImgs[0]);
            mLayoutSkillFreeTreatment.setVisibility(View.VISIBLE);
        } else {
            mSkillFree.setBackgroundResource(isFreeImgs[1]);
            mLayoutSkillFreeTreatment.setVisibility(View.GONE);
        }
    }

    @Override
    public void changeWorkLunchStatus(boolean isWorkLunch) {
        // TODO 改变工作餐状态
        if (isWorkLunch) {
            Drawable drawable = getResources().getDrawable(isFreeImgs[0]);
            /// 这一步必须要做,否则不会显示.
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mWorkingLunch.setCompoundDrawables(null, null, drawable, null);
            }
        } else {
            Drawable drawable = getResources().getDrawable(isFreeImgs[1]);
            /// 这一步必须要做,否则不会显示.
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mWorkingLunch.setCompoundDrawables(null, null, drawable, null);
            }
        }
    }

    @Override
    public void changeStayStatus(boolean isStay) {
        // TODO 改变住宿状态
        if (isStay) {
            Drawable drawable = getResources().getDrawable(isFreeImgs[0]);
            /// 这一步必须要做,否则不会显示.
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mStay.setCompoundDrawables(null, null, drawable, null);
            }
        } else {
            Drawable drawable = getResources().getDrawable(isFreeImgs[1]);
            /// 这一步必须要做,否则不会显示.
            if (null != drawable) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mStay.setCompoundDrawables(null, null, drawable, null);
            }
        }
    }

    @Override
    public void submitSucces() {
        pickUp();
    }

    @Override
    public void jumToTimeManager() {
        TimeManagerActivity.jumpIn(this, null);
    }

    @Override
    public void jumpToMap() {
        bundle = new Bundle();
        bundle.putInt("TAG", 2);
        bundle.putInt(ResidentAddressActivity.KEY, ResidentAddressActivity.TYPE_RETURN_RESULT);
        ResidentAddressActivity.jumpIn(this, bundle);
    }

    @Override
    public void jumpToHomeActivity() {
        HomeActivity.jumpIn(this, null);
        pickUp();
    }

    @Override
    public void progresshide() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void progress() {
        Intent intent = new Intent();
        intent.setClassName(getPackageName(), this.getLocalClassName());
        Log.d("SkillPulishActivity", this.getLocalClassName());
        if (getPackageManager().resolveActivity(intent, 0) != null) {
            ProgressDialog dialog = ProgressDialog.show(SkillPulishActivity.this, "正在连接服务器..", "请稍后..", true, true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(R.layout.custom_progress_dialog);
            TextView tvmessage = (TextView) dialog.findViewById(R.id.tvforprogress);
            tvmessage.setText("请稍后..");
        }

    }


    @Override
    public void pickUp() {
        close();
        overridePendingTransition(R.anim.activity_stay, R.anim.slide_out_to_bottom);
    }

    /**
     * 创建选择图片对话框
     */
    private void createChoosePicDialog() {
        Intent intent = new Intent(this, MultiImageSelectorActivity.class);

        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

        int size = 9 - adapter.getData().size();
        if (adapter.getData().size() < 9) {
            size++;
        } else {
            // 等于9且第九个为空代表只有一个
            if (TextUtils.isEmpty(adapter.getData().get(8))) {
                size = 1;
            }
        }

        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, size);

        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者
        // 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

        startActivityForResult(intent, 10002);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            LogUtil.setLogWithE("onActivityResult", "Canceled Avatar Capture.");
            return;
        }

        if (requestCode == 10002) {
            if (resultCode == RESULT_OK) {
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (null == path || path.isEmpty()) {
                    return;
                } else {
                    for (int i = 0; i < path.size(); i++) {
                        iSkillPulishPresenter.choosePicBack(path.get(i));
                    }
                }
            }
        }
    }

    /*获取技能金额的单位*/
    @Override
    public void getMoneyUnit(List<SkillMoneyUnitEntity> entities) {

        if (entities.size() > 0 && entities != null) {
            moneyUnitEntitys = entities;
        }
        getLocalMoneyUnit();


    }

    private void getLocalMoneyUnit() {
        initPopupWindow();
    }

    /*初始化mSkillMoneyUnit*/
    private void initPopupWindow() {

        View view = LayoutInflater.from(this).inflate(R.layout.popup_money_util, null);
        initMoneyUtilListView(view);
//         获取屏幕高度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int popuHeight = displayMetrics.heightPixels / 4;
//将popuwindow的高度设为屏幕的四分之一
        popManeyUtil = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT
                , popuHeight, false);

        popManeyUtil.setTouchable(true);
        popManeyUtil.setOutsideTouchable(true);
        popManeyUtil.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    /*初始化单位列表*/
    private void initMoneyUtilListView(View view) {

        lvMoneyUnit = (ListView) view.findViewById(R.id.lv_money_unit);
        moneyUnitBaseAdapter = new SkillMoneyUnitBaseAdapter(this, moneyUnitEntitys);
        lvMoneyUnit.setAdapter(moneyUnitBaseAdapter);

        lvMoneyUnit.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSkillUnit.setText(moneyUnitEntitys.get(position).getName());
                popManeyUtil.dismiss();
            }
        });
    }

    /*点击技能售价单位弹出单位列表*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                hideKeyBoard();
                popManeyUtil.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
        }
        return true;
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSkillUnit.getWindowToken(), 0);
    }
}
