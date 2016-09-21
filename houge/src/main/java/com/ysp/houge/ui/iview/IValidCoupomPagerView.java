package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.CoupomEntity;

/** 
 * @描述:  可用优惠券列表View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月30日下午5:11:14
 * @version 1.0
 */
public interface IValidCoupomPagerView extends IBaseRefreshListView<CoupomEntity> {
 /**
 * @描述:  选择
 * @方法名: choose
 * @param position  选则的下表
 * @返回类型 void
 * @创建人 tyn
 * @创建时间 2015年9月30日下午5:12:01
 * @修改人 tyn
 * @修改时间 2015年9月30日下午5:12:01
 * @修改备注
 * @since
 * @throws
 */
void choose(int position);
}
