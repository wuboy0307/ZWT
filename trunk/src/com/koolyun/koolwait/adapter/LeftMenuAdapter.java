package com.koolyun.koolwait.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.koolyun.koolwait.MainActivity;
import com.koolyun.koolwait.R;
import com.koolyun.koolwait.model.AipInterface;
import com.koolyun.koolwait.model.AipResponse;
import com.koolyun.koolwait.model.AipSeatCategoriesResponse.SeatCategory;
import com.koolyun.koolwait.model.AipUpdateQueueStatus;
import com.koolyun.koolwait.model.Queue;

public class LeftMenuAdapter extends BaseAdapter {
	private Context context;
	private SeatCategory []seatCategories;
	private Queue []queues;
	private Handler handler;
	
	public LeftMenuAdapter(Context context, Handler handler, SeatCategory []seatCategories, Queue []queues) {
		this.context = context;
		this.seatCategories = seatCategories;
		this.queues = queues;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return seatCategories.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if(arg1 == null) {
			viewHolder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.left_menu_row, null);
			
			viewHolder.deskNameText1 = (TextView) arg1.findViewById(R.id.deskNameText1);
			viewHolder.useNumberButton = (Button) arg1.findViewById(R.id.useNumberButton);
			
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		
		// 餐桌名称
		viewHolder.deskNameText1.setText(context.getResources().getString(R.string.desk_name,
				seatCategories[arg0].getName(), seatCategories[arg0].getMin_num() + "",
				seatCategories[arg0].getMax_num() + ""));
		
		// 等待桌数
		boolean hasQueueID = false;
		Queue q = null;
		for (Queue queue : queues) {
			if(queue.getSeat_category_id() == seatCategories[arg0].getId()) {
				hasQueueID = true;
				q = queue;
				break;
			}
		}
		if(hasQueueID && q.getDelivered_number() - q.getCalled_number() > 0) { // 等待人数大于0
			viewHolder.useNumberButton.setVisibility(View.VISIBLE);
			viewHolder.useNumberButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							handler.sendEmptyMessage(MainActivity.SHOW_DIALOG);
							AipUpdateQueueStatus aipUpdateQueueStatus = new AipUpdateQueueStatus(
									seatCategories[arg0].getId(), AipUpdateQueueStatus.NEXT);
							AipResponse aipResponse = AipInterface.updateQueueStatus(context, aipUpdateQueueStatus);
							
							Message msg = new Message();
							msg.what = MainActivity.USE_NUMBER_END;
							msg.arg1 = (int) seatCategories[arg0].getId();
							msg.obj = aipResponse;
							handler.sendMessage(msg);
						}
					}).start();
				}
			});
		} else { // 没有等待
			viewHolder.useNumberButton.setVisibility(View.GONE);
		}
		
		return arg1;
	}

	private static class ViewHolder {
		TextView deskNameText1;
		Button useNumberButton;
	}
}
