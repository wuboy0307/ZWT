package com.koolyun.koolwait;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.koolyun.coupon.print.exception.AccessException;
import com.koolyun.coupon.print.ticket.TicketPrint;
import com.koolyun.koolwait.adapter.DeskAdapter;
import com.koolyun.koolwait.bean.WaitDeskPrint;
import com.koolyun.koolwait.fragment.MenuFragment;
import com.koolyun.koolwait.model.AipPushMessageResponse;
import com.koolyun.koolwait.model.AipResponse;
import com.koolyun.koolwait.model.AipSeatCategoriesResponse.SeatCategory;
import com.koolyun.koolwait.model.AipTakeNumberResponse;
import com.koolyun.koolwait.model.AipUpdateQueueStatusResponse;
import com.koolyun.koolwait.model.NetworkCommunication;
import com.koolyun.koolwait.model.OrdersBean;
import com.koolyun.koolwait.model.PushProcessTask;
import com.koolyun.koolwait.model.PushUtils;
import com.koolyun.koolwait.model.Queue;
import com.koolyun.koolwait.receiver.PushMessageReceiver;
import com.koolyun.koolwait.utils.JsonConverter;
import com.koolyun.koolwait.utils.MyLog;

public class MainActivity extends SlidingFragmentActivity {
	private GridView gridView;
	public static boolean active;
	private static OrdersBean mOrdersBean = new OrdersBean();
	private DeskAdapter adapter;
	public static final int SHOW_DIALOG = 0;
	public static final int SEND_NUMBER_END = 1;
	public static final int USE_NUMBER_END = 2;
	private Handler handler;
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle(R.string.hint);
		PushMessageReceiver.setMainActivity(this);
		dialog.setMessage(getResources().getString(R.string.loading_message));
		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				switch (arg0.what) {
				case SHOW_DIALOG:
					dialog.show();
					break;
				case SEND_NUMBER_END: {
					AipResponse aipResponse = (AipResponse) arg0.obj;
					if(aipResponse.getCode() == 0) { // success
						MyLog.e("aipResponse.getResult():"+aipResponse.getResult());
						AipTakeNumberResponse aipTakeNumberResponse = (AipTakeNumberResponse)
								JsonConverter.fromJson(aipResponse.getResult(), AipTakeNumberResponse.class);
						Toast.makeText(MainActivity.this, getResources().getString(R.string.take_number_success,
								aipTakeNumberResponse.getQueue_number().getNumber() + ""), Toast.LENGTH_LONG).show();
						
						int categoryID = arg0.arg1;
						WaitDeskPrint waitDeskPrint = new WaitDeskPrint();
						for (int i = 0; i < mOrdersBean.getAipQueueResponse().getQueues().length; i++) {
							Queue queue = mOrdersBean.getAipQueueResponse().getQueues()[i];
							if((int)queue.getSeat_category_id() == categoryID) {
								queue.setCalled_number(aipTakeNumberResponse.getQueue().getCalled_number());
								queue.setDelivered_number(aipTakeNumberResponse.getQueue().getDelivered_number());
								
								waitDeskPrint.setNumber(aipTakeNumberResponse.getQueue().getDelivered_number());
								waitDeskPrint.setWaitingCount(aipTakeNumberResponse.getQueue().getCalled_number());
								waitDeskPrint.setQrcode(aipTakeNumberResponse.getQueue_number().getQrcode());
								waitDeskPrint.setMerchantName(mOrdersBean.getAipLoginResponse().getRestaurant());
								break;
							}
						}
						for (int i = 0; i < mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories().length; i++) {
							SeatCategory seatCategory = mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories()[i];
							if(seatCategory.getId() == categoryID) {
								waitDeskPrint.setDeskType(seatCategory.getName());
							}
						}
						adapter.notifyDataSetChanged();
						TicketPrint ticketPrint = new TicketPrint();
						try {
							ticketPrint.printWaitingNumber(waitDeskPrint);
						} catch (AccessException e) {
							e.printStackTrace();
						}
					} else {
						Toast.makeText(MainActivity.this, R.string.operate_failure, Toast.LENGTH_LONG).show();
					}

					dialog.dismiss();
					break;
				}
				case USE_NUMBER_END: {
					AipResponse aipResponse = (AipResponse) arg0.obj;
					if(aipResponse.getCode() == 0) { // success
						MyLog.e("aipResponse.getResult():"+aipResponse.getResult());
						AipUpdateQueueStatusResponse aipTakeNumberResponse = (AipUpdateQueueStatusResponse)
								JsonConverter.fromJson(aipResponse.getResult(), AipUpdateQueueStatusResponse.class);
						
						int categoryID = arg0.arg1;
						for (int i = 0; i < mOrdersBean.getAipQueueResponse().getQueues().length; i++) {
							Queue queue = mOrdersBean.getAipQueueResponse().getQueues()[i];
							if((int)queue.getSeat_category_id() == categoryID) {
								queue.setCalled_number(aipTakeNumberResponse.getQueue().getCalled_number());
								queue.setDelivered_number(aipTakeNumberResponse.getQueue().getDelivered_number());
								break;
							}
						}
						adapter.notifyDataSetChanged();
						Toast.makeText(MainActivity.this, R.string.use_number_success, Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(MainActivity.this, R.string.operate_failure, Toast.LENGTH_LONG).show();
					}

					dialog.dismiss();
					break;
				}
				}
				return false;
			}
		});
		// 设置标题栏的标题
//		setTitle("SlidingMenu Properties");

		// 设置是否能够使用ActionBar来滑动
		setSlidingActionBarEnabled(true);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));

		// 设置主界面视图
		setContentView(R.layout.activity_main);

		// 初始化滑动菜单
		initSlidingMenu(savedInstanceState);

		// 初始化组件
		initView();
	}
	
	@Override
	protected void onResume() {
		active = true;
		refreshData();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		active = false;
		super.onPause();
	}

	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 设置滑动菜单的视图
		setBehindContentView(R.layout.menu_frame);
		getFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment(handler, mOrdersBean)).commit();

		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动阴影的图像资源
		// sm.setShadowDrawable(R.drawable.ic_launcher);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		sm.setBehindWidth(400);//设置SlidingMenu菜单的宽度
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridView);
		adapter = new DeskAdapter(MainActivity.this, handler, mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories(),
				mOrdersBean.getAipQueueResponse().getQueues());
		gridView.setAdapter(adapter);
		
		ImageView qrImage = (ImageView) findViewById(R.id.qrImage);
		if(NetworkCommunication.URL.equals(NetworkCommunication.FORMAL_URL)) {
			qrImage.setBackgroundResource(R.drawable.code);
		} else {
			qrImage.setBackgroundResource(R.drawable.code_test);
		}
	}

	/*
	 * 接收百度云推送
	 */
	@SuppressWarnings("null")
	@Override
	protected void onNewIntent(Intent intent) {
		MyLog.e("onNewIntent");
		if (intent != null || intent.getAction() != null || intent.getAction().equals(PushUtils.ACTION_MESSAGE)) {
			String msg = intent.getStringExtra("msg");
			if (msg == null || msg.isEmpty()) {
				return;
			}
			Log.i("Message", msg);

			AipPushMessageResponse response = (AipPushMessageResponse) JsonConverter.fromJson(msg,
					AipPushMessageResponse.class);
			Log.i("response", response.toString());
			// 不是自己发的推送或者某个订单需要更新, 则处理该推送
//			if (!response.getCaused_by().equals(userId) || needUpdateOrderId == response.getValue()) {
//				needUpdateOrderId = 0;
				new PushProcessTask(this, mOrdersBean).execute(response);
//			} else {
//				Log.i(TAG, "Same user id");
//			}
		}
	}

	/**
	 * 如果Activity处于未激活状态（active == false ），调用该方法
	 * 
	 * @param msg
	 */
	public void newPushMessage(String msg) {
		MyLog.e("newPushMessage");
		if (msg == null || msg.isEmpty()) {
			return;
		}
		Log.i("Message", msg);

		AipPushMessageResponse response = (AipPushMessageResponse) JsonConverter.fromJson(msg,
				AipPushMessageResponse.class);
		Log.i("response", response.toString());
//		// 不是自己发的推送或者某个订单需要更新, 则处理该推送
//		if (!response.getCaused_by().equals(userId) || needUpdateOrderId == response.getValue()) {
//			needUpdateOrderId = 0;
			new PushProcessTask(this, mOrdersBean).execute(response);
//		} else {
//			Log.i(TAG, "Same user id");
//		}
	}

	/**
	 * 获取订餐易数据
	 * 
	 * @return
	 */
	public static OrdersBean getOrdersBean() {
		return mOrdersBean;
	}

	/**
	 * 设置订单数据
	 * 
	 * @param ordersBean
	 */
	public static void setOrdersBean(OrdersBean ordersBean) {
		mOrdersBean = ordersBean;
	}

	public void updateQueueData() {
		if(active) {
			refreshData();
		}
	}
	
	private void refreshData() {
		adapter = new DeskAdapter(MainActivity.this, handler,
				mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories(),
				mOrdersBean.getAipQueueResponse().getQueues());
		gridView.setAdapter(adapter);
		
		MenuFragment fragment = (MenuFragment) getFragmentManager().findFragmentById(R.id.menu_frame);
		fragment.setmOrdersBean(mOrdersBean);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.manage:
			Intent intent = new Intent(MainActivity.this, ManageActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.manage, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
