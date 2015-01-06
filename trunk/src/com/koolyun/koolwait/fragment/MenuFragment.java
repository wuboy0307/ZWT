package com.koolyun.koolwait.fragment;

import com.koolyun.koolwait.R;
import com.koolyun.koolwait.adapter.LeftMenuAdapter;
import com.koolyun.koolwait.model.OrdersBean;
import com.koolyun.koolwait.utils.MyLog;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuFragment extends Fragment {
	private Handler handler;
	private ListView listView;
	private LeftMenuAdapter adapter;
	private OrdersBean mOrdersBean;

	public MenuFragment() {
	}
	
	public MenuFragment(Handler handler, OrdersBean mOrdersBean) {
		this.handler = handler;
		this.mOrdersBean = mOrdersBean;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.left_fragment, container, false);
		
		listView = (ListView) v.findViewById(R.id.listView);
		
		adapter = new LeftMenuAdapter(getActivity(), handler, mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories(),
				mOrdersBean.getAipQueueResponse().getQueues());
		listView.setAdapter(adapter);
		
		return v;
	}
	
	public void setmOrdersBean(OrdersBean mOrdersBean) {
		MyLog.e("fragment setmOrdersBean---");
		this.mOrdersBean = mOrdersBean;
		adapter = new LeftMenuAdapter(getActivity(), handler, mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories(),
				mOrdersBean.getAipQueueResponse().getQueues());
		listView.setAdapter(adapter);
	}
}
