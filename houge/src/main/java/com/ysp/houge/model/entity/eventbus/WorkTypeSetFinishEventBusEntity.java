package com.ysp.houge.model.entity.eventbus;

import java.util.List;

import com.ysp.houge.model.entity.bean.WorkTypeEntity;

public class WorkTypeSetFinishEventBusEntity {

	public List<WorkTypeEntity> entities;//现在需要显示的文字
	
	public String list;//增量
	
	public String delList;//减量
}
