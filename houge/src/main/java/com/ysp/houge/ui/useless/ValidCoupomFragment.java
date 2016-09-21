package com.ysp.houge.ui.useless;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.CoupomEntity;
import com.ysp.houge.presenter.IValidCoupomPresenter;
import com.ysp.houge.presenter.impl.ValidCoupomPresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.IValidCoupomPagerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/** 
 * @描述:  可用优惠券Fragment
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月30日下午5:16:21
 * @version 1.0
 */
public class ValidCoupomFragment extends BaseFragment implements IValidCoupomPagerView {
	private PullToRefreshListView mRefreshListView;

	private ListViewHelper<List<CoupomEntity>> listViewHelper;
	private IValidCoupomPresenter<List<CoupomEntity>> iValidCoupomPresenter;
	@Override
	protected void initActionbar(View view) {
	}

	@Override
	protected void initializeViews(View view, Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.mRefreshListView);
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_valid_coupom;
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iValidCoupomPresenter = new ValidCoupomPresenter(this);

		listViewHelper = new ListViewHelper<List<CoupomEntity>>(
				mRefreshListView);
		listViewHelper.setRefreshPresenter(iValidCoupomPresenter);
//		listViewHelper.setAdapter(new PartJobAdatper(getActivity()));
		listViewHelper.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				iValidCoupomPresenter.clickListItem(parent, view,
						position, id);
			}
		});
		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshComplete(CoupomEntity t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMoreComplete(CoupomEntity t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void choose(int position) {
		// TODO Auto-generated method stub
		
	}

}
