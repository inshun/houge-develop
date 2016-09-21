package com.ysp.houge.comparator;

import java.util.Comparator;

import com.ysp.houge.model.entity.bean.SchoolEntity;

/**
 * @描述:根据学校名称进行排序
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月13日上午10:26:05
 * @version 1.0
 */
public class SchoolNameComparator implements Comparator<SchoolEntity> {

	public int compare(SchoolEntity o1, SchoolEntity o2) {
		if (o1.getSortLetter().equals("☆") || o2.getSortLetter().equals("#")) {
			return -1;
		} else if (o1.getSortLetter().equals("#")
				|| o2.getSortLetter().equals("☆")) {
			return 1;
		} else {
			return o1.getSortLetter().compareTo(o2.getSortLetter());
		}
	}

}
