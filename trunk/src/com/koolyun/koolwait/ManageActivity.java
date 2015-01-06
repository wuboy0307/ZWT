package com.koolyun.koolwait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.koolyun.koolwait.adapter.ManageAdapter;
import com.koolyun.koolwait.model.AipInterface;
import com.koolyun.koolwait.model.AipResponse;
import com.koolyun.koolwait.model.AipSeatCategoriesResponse.SeatCategory;
import com.koolyun.koolwait.model.AipUpdateQueueStatus;
import com.koolyun.koolwait.model.AipUpdateQueueStatusResponse;
import com.koolyun.koolwait.model.OrdersBean;
import com.koolyun.koolwait.model.Queue;
import com.koolyun.koolwait.utils.JsonConverter;
import com.koolyun.koolwait.utils.MyLog;

public class ManageActivity extends Activity implements OnClickListener {
	private Handler handler;
	private ProgressDialog dialog;
	public static final int SHOW_DIALOG = 0;
	public static final int GET_QUEUE_END = 1;
	public static final int STOP_ALL_QUEUE = 2;
	private GridView gridView;
	private ManageAdapter adapter;
	private OrdersBean mOrdersBean;
	public static boolean isVisible;
	private List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
	private List<Queue> queueList = new ArrayList<Queue>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage);
		
		dialog = new ProgressDialog(ManageActivity.this);
		dialog.setTitle(R.string.hint);
		dialog.setMessage(getResources().getString(R.string.loading_message));
		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				switch (arg0.what) {
				case SHOW_DIALOG:
					dialog.show();
					break;
				case GET_QUEUE_END: {
					dialog.dismiss();
					
					AipResponse aipResponse = (AipResponse) arg0.obj;
					if(aipResponse != null && aipResponse.getCode() == 0) { // success
						if(aipResponse.getCode() == 0) { // success
							AipUpdateQueueStatusResponse aipUpdateQueueStatusResponse = (AipUpdateQueueStatusResponse)
									JsonConverter.fromJson(aipResponse.getResult(), AipUpdateQueueStatusResponse.class);
							MyLog.e("aipUpdateQueueStatusResponse.getQueues():"+aipUpdateQueueStatusResponse.getQueue());
							
							int categoryID = arg0.arg1;
							MyLog.e("categoryID:"+categoryID);
							if(aipUpdateQueueStatusResponse.getQueue() == null ||
									aipUpdateQueueStatusResponse.getQueue().getStatus() == 2) { // 停止排队
								List<Queue> newQueueList = new ArrayList<Queue>();
								newQueueList.addAll(queueList);
								for (int i = 0; i < queueList.size(); i++) {
									Queue queue = newQueueList.get(i);
									if((int)queue.getSeat_category_id() == categoryID) {
										MyLog.e("queue.getSeat_category_id() == categoryID");
										try {
//											newQueueList.remove(queue);
											queue.setStatus(2);
											MyLog.e("newQueueList.remove(queue)");
										} catch (Exception e) {
											queue = null;
											e.printStackTrace();
										}
										break;
									}
								}
								queueList = newQueueList;
								MyLog.e("newQueueList.size():"+newQueueList.size());
							} else {
								com.koolyun.koolwait.model.AipUpdateQueueStatusResponse.Queue q1 = aipUpdateQueueStatusResponse.getQueue();
								boolean isHave = false;
								for (Queue queue : queueList) {
									if(queue.getSeat_category_id() == categoryID) {
										queue.setCalled_number(q1.getCalled_number());
										queue.setDelivered_number(q1.getDelivered_number());
										queue.setStatus(q1.getStatus());
										isHave = true;
										break;
									}
								}
								if(!isHave) {
									Queue q2 = new Queue();
									q2.setCalled_number(q1.getCalled_number());
									q2.setDelivered_number(q1.getDelivered_number());
									q2.setSeat_category_id(categoryID);
									queueList.add(q2);
								}
							}
						}
						MyLog.e("queueList.size():"+queueList.size());
						adapter = new ManageAdapter(ManageActivity.this, handler, 
								seatCategoryList, queueList);
						gridView.setAdapter(adapter);
					} else {
						Toast.makeText(ManageActivity.this, R.string.operate_failure, Toast.LENGTH_LONG).show();
					}
					break;
				}
				case STOP_ALL_QUEUE: {
					dialog.dismiss();
					AipResponse aipResponse = (AipResponse) arg0.obj;
					if(aipResponse.getCode() == 0) { // success
						queueList.clear();
					}
					adapter = new ManageAdapter(ManageActivity.this, handler, 
							seatCategoryList, queueList);
					gridView.setAdapter(adapter);
					break;
				}
				}
				return false;
			}
		});
		
		Button stopQueueButton = (Button) findViewById(R.id.stopQueueButton);
		stopQueueButton.setOnClickListener(this);
		
		mOrdersBean = MainActivity.getOrdersBean();
		
		seatCategoryList = Arrays.asList(mOrdersBean.getAipSeatCategoriesResponse().getSeat_categories());
		queueList = new ArrayList<Queue>(Arrays.asList(mOrdersBean.getAipQueueResponse().getQueues()));
		
		gridView = (GridView) findViewById(R.id.gridView);
		adapter = new ManageAdapter(ManageActivity.this, handler, 
				seatCategoryList, queueList);
		gridView.setAdapter(adapter);
		
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.blue));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.manage);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.stopQueueButton:
			new AlertDialog.Builder(ManageActivity.this).setTitle(R.string.hint)
				.setMessage("确定将所有的排队数据都清零？")
				.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						new Thread(new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(ManageActivity.SHOW_DIALOG);
								AipUpdateQueueStatus aipUpdateQueueStatus = new AipUpdateQueueStatus(
										0, AipUpdateQueueStatus.STOP_ALL_QUEUE);
								AipResponse aipResponse = AipInterface.updateQueueStatus(ManageActivity.this,
										aipUpdateQueueStatus);
								MyLog.e("aipResponse:"+aipResponse);
								Message msg = new Message();
								msg.what = ManageActivity.STOP_ALL_QUEUE;
								msg.obj = aipResponse;
								handler.sendMessage(msg);
							}
						}).start();
					}
				}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).show();
			break;
		}
	}
	
	@Override
	protected void onResume() {
		isVisible = true;
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		isVisible = false;
		super.onPause();
	}
}
