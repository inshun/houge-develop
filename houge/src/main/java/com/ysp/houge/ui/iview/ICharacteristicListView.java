package com.ysp.houge.ui.iview;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.model.entity.bean.CharacteristicEntity;

/**
 * @描述:注册时选择特点页面的MVP模式中的view层的接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月22日下午8:35:47
 * @version 1.0
 */
public interface ICharacteristicListView extends IBaseView {

	void setList(List<CharacteristicEntity> characteristicEntities);

	void JumpToNextPage(ArrayList<CharacteristicEntity> characteristicChoosed);
}
