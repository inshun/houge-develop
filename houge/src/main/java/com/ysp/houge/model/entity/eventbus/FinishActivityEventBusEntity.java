package com.ysp.houge.model.entity.eventbus;

/**
 * @描述:关闭页面的通知类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月16日下午1:29:33
 * @version 1.0
 */
public class FinishActivityEventBusEntity {
	private FinishViewType finishViewType;

	/**
	 * @描述
	 * @param finishViewType
	 */
	public FinishActivityEventBusEntity(FinishViewType finishViewType) {
		super();
		this.finishViewType = finishViewType;
	}

	/**
	 * @return the finishViewType
	 */
	public FinishViewType getFinishViewType() {
		return finishViewType;
	}

	/**
	 * @param finishViewType
	 *            the finishViewType to set
	 */
	public void setFinishViewType(FinishViewType finishViewType) {
		this.finishViewType = finishViewType;
	}

	public enum FinishViewType {
		/**
		 * @字段：ChooseLoginOrRegisterActivity
		 * @功能描述：关闭选择登录还是注册页面
		 * @创建人：tyn
		 * @创建时间：2015年9月16日下午1:30:02
		 */
		ChooseLoginOrRegisterActivity,

		/**
		 * @字段：MePrePaidStepOneActivity
		 * @功能描述：关闭预支工资第一步页面
		 * @创建人：tyn
		 * @创建时间：2015年9月20日下午5:13:50
		 */
		MePrePaidStepOneActivity,

		/**
		 * @字段：MePrePaidStepTwoActivity
		 * @功能描述：关闭预支工资第二步页面
		 * @创建人：tyn
		 * @创建时间：2015年9月20日下午5:13:52
		 */
		MePrePaidStepTwoActivity
	}

}
