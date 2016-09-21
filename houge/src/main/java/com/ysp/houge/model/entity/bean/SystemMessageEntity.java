/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.ysp.houge.model.entity.bean;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ysp.houge.model.entity.db.ItemMessageEntity.MessageType;

/**
 * @描述: 系统消息实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日上午11:47:36
 * @version 1.0
 */
@DatabaseTable(tableName = "system_message")
public class SystemMessageEntity implements Serializable {
    public String id;
    public String title;
    public String user_type;
    public int type;
    public String created_at;
    public String content;
    public String is_new;
    public String pic;
    public String count;

}
