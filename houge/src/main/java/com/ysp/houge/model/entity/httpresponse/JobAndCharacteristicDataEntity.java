package com.ysp.houge.model.entity.httpresponse;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.bean.CharacteristicEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月30日下午8:35:56
 * @version 1.0
 */
public class JobAndCharacteristicDataEntity {
	/**
	 * @字段：cando
	 * @功能描述：你能做什么
	 * @创建人：tyn
	 * @创建时间：2015年6月30日下午8:35:19
	 */
	@SerializedName(value = "cando")
	private List<WorkTypeEntity> cando;
	/**
	 * @字段：girl_characteristic
	 * @功能描述：女生特点列表
	 * @创建人：tyn
	 * @创建时间：2015年6月30日下午8:35:22
	 */
	@SerializedName(value = "characteristic")
	private List<CharacteristicEntity> characteristic;

	/**
	 * @return the cando
	 */
	public List<WorkTypeEntity> getCando() {
		return cando;
	}

	/**
	 * @param cando
	 *            the cando to set
	 */
	public void setCando(List<WorkTypeEntity> cando) {
		this.cando = cando;
	}

	/**
	 * @return the characteristic
	 */
	public List<CharacteristicEntity> getCharacteristic() {
		return characteristic;
	}

	/**
	 * @param characteristic
	 *            the characteristic to set
	 */
	public void setCharacteristic(List<CharacteristicEntity> characteristic) {
		this.characteristic = characteristic;
	}

}
