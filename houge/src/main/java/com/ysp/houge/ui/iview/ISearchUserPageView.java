package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * 描述： 用户搜索View层接口
 *
 * @ClassName: ISearchUserPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午10:26:44
 * 
 *        版本: 1.0
 */
public interface ISearchUserPageView extends IBaseRefreshListView<List<UserInfoEntity>> {

    void setSearchText(String searchText);
}
