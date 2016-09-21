package com.ysp.houge.presenter.impl;

import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.IOrderModel;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;
import com.ysp.houge.model.impl.OrderModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IOrderDetailsFragmentPrsenter;
import com.ysp.houge.ui.iview.IOrderDetailsFragmentView;
import com.ysp.houge.ui.order.OrderDetailsFragment;
import com.ysp.houge.ui.order.PayActivity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

/**
 * 描述： 订单详情Fragemtn Presenter层
 *
 * @ClassName: OrderDetailsFragmentPresenter
 * @author: hx
 * @date: 2015年12月30日 下午3:11:46
 * <p/>
 * 版本: 1.0
 */
public class OrderDetailsFragmentPresenter extends BasePresenter<IOrderDetailsFragmentView>
        implements IOrderDetailsFragmentPrsenter {
    private String order_id;
    private String status;
    private int operation;

    private IOrderModel iOrderModel;
    private OrderDetailsEntity orderDetailsEntity;
    private OrderDetailsFragment orderDetailsFragment;
    private IOrderDetailsFragmentView iOrderDetailsFragmentView;

    public OrderDetailsFragmentPresenter(IOrderDetailsFragmentView view) {
        super(view);
    }


    @Override
    public void initModel() {
        iOrderModel = new OrderModelImpl();
    }

    @Override
    public void setOrderInfo(String order_id, String status, int operation, OrderDetailsFragment view) {
        this.order_id = order_id;
        this.status = status;
        this.operation = operation;
        this.iOrderDetailsFragmentView = view;
    }

    @Override
    public void requstOrderDetails() {
        mView.showProgress();
        if (TextUtils.isEmpty(order_id)) {
            mView.showToast("错误订单号");
            return;
        }
        iOrderModel.getOrderDetails(order_id, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                if (null != data && data instanceof OrderDetailsEntity) {
                    orderDetailsEntity = (OrderDetailsEntity) data;
                    mView.setOrderInfo(orderDetailsEntity);
                    //如果需要进行订单操作,则去请求订单操作接口
                    if (operation != -1) {
                        switch (operation) {
                            case OnItemClickListener.CLICK_OPERATION_ORDER_FUNCTION:
                                statusOperate();
                                break;
                            //此处buyer.mobile拿到的是我卖出的买家的电话
                            case OnItemClickListener.CLICK_OPERATION_CALL_TO_BUY:
                                mView.callPhone(orderDetailsEntity.buyer.mobile);
                                break;
                            case OnItemClickListener.CLICK_OPERATION_IM_TO_BUY:
                                mView.jumpToChatPage();
                                break;
                            case OnItemClickListener.CLICK_OPERATION_CALL_TO_SALE:
                                mView.callPhone(orderDetailsEntity.seller.mobile);
                                break;
                            case OnItemClickListener.CLICK_OPERATION_IM_TO_SALE:
                                mView.jumpToChatPage();
                                break;
                        }
                        operation = -1;

                    }
                } else {
                    mView.showToast("无相关订单信息");
                    mView.close();
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                mView.showToast("订单异常，请您仔细检查");
                mView.close();
            }
        });
    }

    @Override
    public void goodClick() {
        if (MyApplication.getInstance().getCurrentUid() == orderDetailsEntity.buyer.id) {
            status = "buy";
        } else {
            status = "sale";
        }
        if (TextUtils.equals(this.status, "buy")) {
            mView.jumpToSkilldetail(orderDetailsEntity.good.id);
        }
        if (TextUtils.equals(this.status, "sale")) {
            mView.jumpToNeedDetail(orderDetailsEntity.good.id);
        }

//        switch (orderDetailsEntity.order.type) {
//            case OrderDetailsEntity.ORDER_TYPE_SKILL:
//                mView.jumpToSkilldetail(orderDetailsEntity.good.id);
//                break;
//            case OrderDetailsEntity.ORDER_TYPE_TASK:
//                mView.jumpToNeedDetail(orderDetailsEntity.good.id);
//                break;
//        }
    }

    @Override
    public void userClick() {
        mView.jumpToUser();
    }

    @Override
    public void statusOperate() {
        BDLocation bdLocation = MyApplication.getInstance().getBdLocation();
        String street = bdLocation.getStreet();
//        if (null == bdLocation || TextUtils.isEmpty(street)){
//            mView.showToast("获取位置信息错误");
//            return;
//        }

        int status = orderDetailsEntity.order.status;
        switch (status) {
            // 支付
            case OrderEntity.STATUS_WAIT_PAY:
                Bundle bundle = new Bundle();
                bundle.putString(PayActivity.NAME, orderDetailsEntity.good.userInfo.nick);
                bundle.putString(PayActivity.ORDER_ID, orderDetailsEntity.order.order_id);
                bundle.putInt(PayActivity.PAY_TYPE, orderDetailsEntity.order.pay_type);
                int money = -1;
                try {
                    money = Integer.parseInt(orderDetailsEntity.good.price);
                } catch (Exception e) {
                }
//                if (money == -1) {
//                    bundle.putString(PayActivity.PRICE, "免薪实习");
//                } else{
                    money *= orderDetailsEntity.order.quantity;
                    bundle.putString(PayActivity.PRICE, String.valueOf(money));
//                }
                bundle.putString(PayActivity.TITLE, orderDetailsEntity.good.title);
                mView.jumpToPay(bundle);
                break;
            // 等待服务
            case OrderEntity.STATUS_WAIT_SERVICE:
                changeStatus(OrderEntity.STATUS_DEPART, street, MyApplication.LOG_STATUS_SELLER);
                break;
            // 开始服务
            case OrderEntity.STATUS_DEPART:
                changeStatus(OrderEntity.STATUS_IN_SERVICE, "", MyApplication.LOG_STATUS_SELLER);
                break;
            // 结束服务
            case OrderEntity.STATUS_IN_SERVICE:
                changeStatus(OrderEntity.STATUS_SERVICE_FINISH, street, MyApplication.LOG_STATUS_SELLER);
                break;
            // 确认卖家家完成(确认完成，进入待评价)
            case OrderEntity.STATUS_SERVICE_FINISH:
                //如果是当面付订单，先进入付款状态
                if (orderDetailsEntity.order.pay_type == OrderEntity.PAY_TYPE_FACE_TO_FACE) {
                    Bundle b = new Bundle();
                    b.putString(PayActivity.NAME, orderDetailsEntity.good.userInfo.nick);
                    b.putString(PayActivity.ORDER_ID, orderDetailsEntity.order.order_id);
                    b.putInt(PayActivity.PAY_TYPE, orderDetailsEntity.order.pay_type);
                    int m = -1;
                    try {
                        m = Integer.parseInt(orderDetailsEntity.good.price);
                    } catch (Exception e) {
                    }
//                    if (m == -1) {
//                        b.putString(PayActivity.PRICE, "免薪实习");
//                    } else {
                        m *= orderDetailsEntity.order.quantity;
                        b.putString(PayActivity.PRICE, String.valueOf(m));
//                    }
                    b.putString(PayActivity.TITLE, orderDetailsEntity.good.title);
                    mView.jumpToPay(b);
                    return;
                }
                changeStatus(OrderEntity.STATUS_WAIT_COMMENT, "", MyApplication.LOG_STATUS_BUYER);
                break;
            // 评价
            case OrderEntity.STATUS_WAIT_COMMENT:
                // 处理双方都评论完成的状况(这个时候订单进入已完成状态)
                if (orderDetailsEntity.order.is_seller_rate != 0 && orderDetailsEntity.order.is_buyer_rate != 0) {
                    changeStatus(OrderEntity.STATUS_FINISH, "", MyApplication.LOG_STATUS_SELLER);
                } else {
                    mView.jumpToComment(order_id);
                }
                break;
            // 再来一单
            case OrderEntity.STATUS_FINISH:
                com.ysp.houge.model.entity.bean.OrderEntity orderEntity = new com.ysp.houge.model.entity.bean.OrderEntity();
                orderEntity.skillDetailEntity = orderDetailsEntity.good;
                orderEntity.num = orderDetailsEntity.order.quantity;
                double price = 0.0;
                try {
                    price = Double.parseDouble(orderDetailsEntity.order.total_fee);
                } catch (Exception e) {
                }
                orderEntity.totalMoney = price;
                mView.jumpToOrder(orderEntity);
                break;
        }
    }

    @Override
    public void callPhone() {
        //得到买家或者卖家的信息  1 or  2
//        int status = orderDetailsEntity.order.status;
//        if (status == OrderEntity.STATUS_CLOSE || status == OrderEntity.STATUS_FINISH || status == OrderEntity.STATUS_FREEZE || status == OrderEntity.STATUS_STATUS_EXPIRE) {
//            mView.showToast("您当前无法拨打对方电话，请联系客服");
//        } else {

        /*这是推送过来的状态*/
        if (MyApplication.getInstance().getCurrentUid() == orderDetailsEntity.buyer.id) {
            status = "buy";
        } else {
            status = "sale";
        }


        /*这是从订单列表进入的状态*/
        if (TextUtils.equals(this.status, "buy")) {
            mView.callPhone(orderDetailsEntity.seller.mobile);
        }
        if (TextUtils.equals(this.status, "sale")) {
            mView.callPhone(orderDetailsEntity.buyer.mobile);
        }
    }
//    }

    @Override
    public void sendMessage() {
        mView.jumpToChatPage();
    }


    @Override
    public void cancleOrder() {

        //对订单状态进行判断
        switch (orderDetailsEntity.order.status) {
            //取消订单的几个状态
            case OrderEntity.STATUS_WAIT_PAY:
            case OrderEntity.STATUS_WAIT_SERVICE:
                changeStatus(OrderEntity.STATUS_CLOSE, "", MyApplication.LOG_STATUS_BUYER);
                break;

            //申请退款逻辑有问题，暂时注释，以后再修改
            //

            //退款的几个状态
//            case OrderEntity.STATUS_DEPART:
//            case OrderEntity.STATUS_IN_SERVICE:
//            case OrderEntity.STATUS_SERVICE_FINISH:
//
//                if (TextUtils.equals(status, "buy")) {
//                    //申请退款
//                    mView.showTrefundReasonDialog();
//                } else if (TextUtils.equals(status, "sale")) {
//                    //同意退款
//                    confirmRefund(order_id);
//                }
//                //如果根据推送的订单信息，状态就为""
//                //暂时这样判断，以后服务器有了status 注释
//                if (TextUtils.equals(status, "")) {
//                    if (iOrderDetailsFragmentView.getBtnText().equals("申请退款")) {
//                        mView.showTrefundReasonDialog();
//                    } else if (iOrderDetailsFragmentView.getBtnText().equals("同意退款")) {
//                        confirmRefund(order_id);
//                    }
//                }
        }
    }

    @Override
    public void changePrice() {
        mView.showChangePirceDialog();
    }

    @Override
    public void changePrice(String price) {
        double money = 0.0;
        try {
            money = Double.parseDouble(price);
        } catch (Exception e) {
            mView.showToast("错误的价格");
            return;
        }

        mView.showProgress();
        iOrderModel.changePrice(order_id, money, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                mView.showToast("改价成功");
                // 成功了刷新页面
                requstOrderDetails();
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                switch (errorCode) {
                    case ResponseCode.TIP_ERROR:
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;
                }
            }
        });
    }

    private void changeStatus(final int status, String tip, int apt) {
        //此处判断对取消订单有作用，但是对其他的Button 逻辑有问题
        // if (DoubleClickUtil.isFastClick())
        // return;
        mView.showProgress();
        iOrderModel.changeOrderStatus(order_id, status, tip, apt, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                if (data != null && data instanceof String) {
                    requstOrderDetails();
                    if (status == OrderEntity.STATUS_CLOSE) {
                        mView.showToast("订单已关闭");
                    }
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                switch (errorCode) {
                    case ResponseCode.TIP_ERROR:
                        if (TextUtils.equals(message, "0x0001")) {
                            mView.showToast("定位信息错误");
                            return;
                        }
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;
                }
            }
        });
    }

    @Override
    public void requesTrefund(String order_id, String refund_content) {
        mView.showProgress();
        iOrderModel.requesTrefund(order_id, refund_content, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                mView.showToast("退款已申请，等待卖家确认");
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                switch (errorCode) {
                    case ResponseCode.TIP_ERROR:
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;

                }
            }
        });
    }

    @Override
    public void confirmRefund(String order_id) {
            mView.showProgress();
            iOrderModel.confirmRefund(order_id, new OnNetResponseCallback() {
                @Override
                public void onSuccess(Object data) {
                    mView.hideProgress();
                    mView.showToast("您已经同意了买家的退款申请");
                    //TODO: 同意退款申请网络请求还是有问题
                    // 这个地方网络请求刚开始进不了，虽然显示没问题，但是跳转逻辑还是有问题，
                    //暂时让他成功了重新请求一下接口
                    requstOrderDetails();
                }

                @Override
                public void onError(int errorCode, String message) {
                    mView.hideProgress();

                    switch (errorCode) {
                        case ResponseCode.TIP_ERROR:
                            mView.showToast(message);
                            break;
                        default:
                            mView.showToast(R.string.request_failed);
                            break;
                    }
                }
            });
    }
}
