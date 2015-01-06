package com.koolyun.koolwait.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.koolyun.koolwait.ManageActivity;
import com.koolyun.koolwait.R;
import com.koolyun.koolwait.model.AipInterface;
import com.koolyun.koolwait.model.AipResponse;
import com.koolyun.koolwait.model.AipSeatCategoriesResponse.SeatCategory;
import com.koolyun.koolwait.model.AipUpdateQueueStatus;
import com.koolyun.koolwait.model.Queue;
import com.koolyun.koolwait.utils.MyLog;

public class ManageAdapter extends BaseAdapter {
	private Context context;
	private Handler handler;
	private List<SeatCategory> seatCategoryList = new ArrayList<SeatCategory>();
	private List<Queue> queueList = new ArrayList<Queue>();
	
	public ManageAdapter(Context context, Handler handler2,
			List<SeatCategory> seatCategoryList, List<Queue> queueList) {
		this.context = context;
		this.seatCategoryList = seatCategoryList;
		this.queueList = queueList;
		this.handler = handler2;
	}

	@Override
	public int getCount() {
		return seatCategoryList.size();
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
		final ViewHolder viewHolder;
		if(arg1 == null) {
			viewHolder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.manage_gridview_item, null);
			
			viewHolder.checkBox = (Button) arg1.findViewById(R.id.checkBox);
			viewHolder.deskNameText = (TextView) arg1.findViewById(R.id.deskNameText);
			viewHolder.waitDeskNumberText = (TextView) arg1.findViewById(R.id.waitDeskNumberText);
			viewHolder.nextNumberText = (TextView) arg1.findViewById(R.id.nextNumberText);
			
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		
		// 餐桌名称
		viewHolder.deskNameText.setText(context.getResources().getString(R.string.desk_name,
				seatCategoryList.get(arg0).getName(), seatCategoryList.get(arg0).getMin_num() + "",
				seatCategoryList.get(arg0).getMax_num() + ""));
		
		// 等待桌数
		boolean hasQueueID = false;
		Queue q = null;
		for (Queue queue : queueList) {
			if(queue == null) continue;
			if(queue.getSeat_category_id() == seatCategoryList.get(arg0).getId()) {
				hasQueueID = true;
				q = queue;
				break;
			}
		}
		
		if(hasQueueID) { // 有正在等待的桌子
			if(q.getStatus() == 2) {
				viewHolder.checkBox.setSelected(false);
			} else {
				viewHolder.checkBox.setSelected(true);
			}
			
			viewHolder.waitDeskNumberText.setText(q.getDelivered_number()-q.getCalled_number() + "");
			// 下一位
			viewHolder.nextNumberText.setVisibility(View.VISIBLE);
			viewHolder.nextNumberText.setText(q.getCalled_number() + "");
		} else { // 没有等待
			viewHolder.checkBox.setSelected(false);
			viewHolder.checkBox.setTextColor(context.getResources().getColor(R.color.gray));
			
			viewHolder.nextNumberText.setVisibility(View.GONE);
			viewHolder.waitDeskNumberText.setText("0");
		}
		viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						handler.sendEmptyMessage(ManageActivity.SHOW_DIALOG);
						int operateCode;
						boolean isHave = false;
						Queue q = null;
						for (Queue queue : queueList) {
							if(queue == null) continue;
							if(queue.getSeat_category_id() == seatCategoryList.get(arg0).getId()) {
								q = queue;
								break;
							}
						}
						if(q != null) {
							isHave = true;
						}
						if(isHave) {
							if(!viewHolder.checkBox.isSelected()) {
								operateCode = AipUpdateQueueStatus.RESUME_QUEUE;
							} else {
								operateCode = AipUpdateQueueStatus.PAUSE_QUEUE;
							}
						} else {
							if(!viewHolder.checkBox.isSelected()) {
								operateCode = AipUpdateQueueStatus.CREATE_QUEUE;
								MyLog.e("CREATE_QUEUE----");
							} else {
								operateCode = AipUpdateQueueStatus.PAUSE_QUEUE;
							}
						}
						AipUpdateQueueStatus aipUpdateQueueStatus = new AipUpdateQueueStatus(
								seatCategoryList.get(arg0).getId(), operateCode);
						AipResponse aipResponse = AipInterface.updateQueueStatus(context,
								aipUpdateQueueStatus);
						MyLog.e("aipResponse:"+aipResponse);
						Message msg = new Message();
						msg.what = ManageActivity.GET_QUEUE_END;
						msg.arg1 = (int) seatCategoryList.get(arg0).getId();
						msg.obj = aipResponse;
						handler.sendMessage(msg);
					}
				}).start();
			}
		});
		
		return arg1;
	}
	
	private static class ViewHolder {
		Button checkBox;
		TextView deskNameText;
		TextView waitDeskNumberText;
		TextView nextNumberText;
	}
}
