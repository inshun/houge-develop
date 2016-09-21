package com.ysp.houge.ui.iview;

import java.util.List;

/**
 * 描述： 订单状态View层接口
 *
 * @ClassName: IOrderStatusPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月16日 下午3:57:07
 * 
 *        版本: 1.0
 */
public interface IOrderStatusPageView extends IBaseRefreshListView<List<String>> {
    void setOrderId(String order_id);
}
