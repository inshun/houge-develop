package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.CharacteristicEntity;

/**
 * @描述:兼职详情页面的层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月22日下午3:06:16
 * @version 1.0
 */
public interface IPartTimeJobDetailPageView extends IBaseView {

	/**
	 * @描述:拨打电话
	 * @方法名: callTelephone
	 * @param telephoneNumber
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:07:08
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:07:08
	 * @修改备注
	 * @since
	 * @throws
	 */
	void callTelephone(String telephoneNumber);

	/**
	 * @描述:发送短信
	 * @方法名: sendMessage
	 * @param message
	 * @param telephone
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:07:17
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:07:17
	 * @修改备注
	 * @since
	 * @throws
	 */
	void sendMessage(String message, String telephone);

	/**
	 * @描述:显示价格和单位
	 * @方法名: showPriceAndUnit
	 * @param price
	 * @param unit
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:08:36
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:08:36
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showPriceAndUnit(String price, String unit);

	/**
	 * @描述:显示兼职名称
	 * @方法名: showJobName
	 * @param jobName
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:10:28
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:10:28
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showJobName(String jobName);

	/**
	 * @描述:显示结算方式
	 * @方法名: showSettlementState
	 * @param settle
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:10:38
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:10:38
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showSettlementState(String settle);

	/**
	 * @描述:显示地址
	 * @方法名: showAddress
	 * @param address
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:20:53
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:20:53
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showAddress(String address);

	/**
	 * @描述:显示开始时间
	 * @方法名: showTime
	 * @param time
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:20:46
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:20:46
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showStartTime(String time);

	/**
	 * @描述:显示结束时间
	 * @方法名: showEndTime
	 * @param time
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午5:42:23
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午5:42:23
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showEndTime(String time);

	/**
	 * @描述:显示人数
	 * @方法名: showNeedPeopleCount
	 * @param count
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:20:33
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:20:33
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showNeedPeopleCount(String count);

	/**
	 * @描述:显示需要的帮助
	 * @方法名: showNeedHelpIntroduction
	 * @param introduction
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:20:17
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:20:17
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showNeedHelpIntroduction(String introduction);

	/**
	 * @描述:显示特点列表
	 * @方法名: showNeedCharacteristic
	 * @param characteristicEntities
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:20:08
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:20:08
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showNeedCharacteristic(
			List<CharacteristicEntity> characteristicEntities);

	/**
	 * @描述:显示其他说明
	 * @方法名: showOtherIntroduction
	 * @param introduction
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:20:00
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:20:00
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showOtherIntroduction(String introduction);

	/**
	 * @描述:显示发布者的姓名
	 * @方法名: showPublisherName
	 * @param name
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:19:52
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:19:52
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showPublisherName(String name);

	/**
	 * @描述:显示发布者的头像
	 * @方法名: showPublisherAvatar
	 * @param avatarUrl
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:19:41
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:19:41
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showPublisherAvatar(String avatarUrl);

	/**
	 * @描述:显示确认拨打电话的对话框
	 * @方法名: showSubmitCallDialog
	 * @param mobile
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月22日下午3:40:49
	 * @修改人 tyn
	 * @修改时间 2015年8月22日下午3:40:49
	 * @修改备注
	 * @since
	 * @throws
	 */
	void showSubmitCallDialog(String mobile);
}
