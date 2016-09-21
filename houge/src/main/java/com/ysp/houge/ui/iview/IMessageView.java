package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.WebEntity;
import com.ysp.houge.model.entity.db.MessageEntity;

/**
 * 描述： 消息列表页面View层接口
 *
 * @ClassName: IMessageView
 * 
 * @author: hx
 * 
 * @date: 2016年1月2日 下午2:15:55
 * 
 *        版本: 1.0
 */
public interface IMessageView extends IBaseRefreshListView<List<MessageEntity>> {

    void openH5(WebEntity webEntity);
}
