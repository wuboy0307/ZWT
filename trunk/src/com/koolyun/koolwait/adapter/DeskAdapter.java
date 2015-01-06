package com.koolyun.koolwait.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.koolyun.koolwait.MainActivity;
import com.koolyun.koolwait.R;
import com.koolyun.koolwait.model.AipInterface;
import com.koolyun.koolwait.model.AipResponse;
import com.koolyun.koolwait.model.AipSeatCategoriesResponse.SeatCategory;
import com.koolyun.koolwait.model.AipTakeNumber;
import com.koolyun.koolwait.model.Queue;

public class DeskAdapter extends BaseAdapter {
	private Context context;
	private SeatCategory []seatCategories;
	private Queue []queues;
	private Handler handler;
	
	public DeskAdapter(Context context, Handler handler, SeatCategory []seatCategories, Queue []queues) {
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
			arg1 = LayoutInflater.from(context).inflate(R.layout.main_gridview_item, null);
			
			viewHolder.deskHintText = (TextView) arg1.findViewById(R.id.deskHintText);
			viewHolder.sendNumberButton = (Button) arg1.findViewById(R.id.sendNumberButton);
			viewHolder.deskNameText = (TextView) arg1.findViewById(R.id.deskNameText);
			viewHolder.waitDeskNumberText = (TextView) arg1.findViewById(R.id.waitDeskNumberText);
			viewHolder.nextNumberText = (TextView) arg1.findViewById(R.id.nextNumberText);
			
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		
		// 餐桌名称
		viewHolder.deskNameText.setText(context.getResources().getString(R.string.desk_name,
				seatCategories[arg0].getName(), seatCategories[arg0].getMin_num() + "",
				seatCategories[arg0].getMax_num() + ""));
		
		// 等待桌数
		boolean hasQueueID = false;
		Queue q = null;
		for (Queue queue : queues) {
			if(queue.getSeat_category_id() == seatCategories[arg0].getId()) {
				q = queue;
				if(queue.getStatus() == 2) continue;
				hasQueueID = true;
				break;
			}
		}
		if(hasQueueID) { // 有正在等待的桌子
			viewHolder.waitDeskNumberText.setText(q.getDelivered_number()-q.getCalled_number() + "");
			
			// 下一位
			viewHolder.nextNumberText.setVisibility(View.VISIBLE);
			viewHolder.nextNumberText.setText(q.getCalled_number() + "");
		
			viewHolder.deskHintText.setVisibility(View.GONE);
			
			viewHolder.sendNumberButton.setVisibility(View.VISIBLE);
			viewHolder.sendNumberButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					LayoutInflater factory = LayoutInflater.from(context);
//					final View textEntryView = factory.inflate(R.layout.dialog_edittext_layout, null);
//					AlertDialog dlg = new AlertDialog.Builder(context)
//						.setTitle(R.string.hint)
//						.setView(textEntryView)
//						.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog, int whichButton) {
//									dialog.dismiss();
//									EditText secondPwd = (EditText) textEntryView.findViewById(R.id.phoneEdit);
//									final String inputPwd = secondPwd.getText().toString();
//									
									new Thread(new Runnable() {
										@Override
										public void run() {
											handler.sendEmptyMessage(MainActivity.SHOW_DIALOG);
//											AipResponse aipResponse = AipInterface.takeQueueNumber(context,
//													new AipTakeNumber(seatCategories[arg0].getId(), inputPwd));
											AipResponse aipResponse = AipInterface.takeQueueNumber(context,
													new AipTakeNumber(seatCategories[arg0].getId(), "00000000000"));
											
											Message msg = new Message();
											msg.what = MainActivity.SEND_NUMBER_END;
											msg.arg1 = (int) seatCategories[arg0].getId();
											msg.obj = aipResponse;
											handler.sendMessage(msg);
										}
									}).start();
//								}
//							})
//						.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog, int whichButton) {
//									dialog.dismiss();
//								}
//							}).create();
//					dlg.show();
				}
			});
		} else {
			if(q != null && q.getStatus() == 2) {
				viewHolder.waitDeskNumberText.setText(q.getDelivered_number()-q.getCalled_number() + "");
				
				// 下一位
				viewHolder.nextNumberText.setVisibility(View.VISIBLE);
				viewHolder.nextNumberText.setText(q.getCalled_number() + "");
				
				viewHolder.deskHintText.setVisibility(View.VISIBLE);
				viewHolder.deskHintText.setText(R.string.pause_send_number);
				viewHolder.deskHintText.setTextColor(context.getResources().getColor(R.color.gray));
			} else { // 没有等待
				viewHolder.nextNumberText.setVisibility(View.GONE);
				viewHolder.waitDeskNumberText.setText("0");
			
				viewHolder.deskHintText.setVisibility(View.VISIBLE);
				viewHolder.deskHintText.setText(R.string.eat_immediately);
				viewHolder.deskHintText.setTextColor(context.getResources().getColor(R.color.blue));
			}
			
			viewHolder.sendNumberButton.setVisibility(View.GONE);
		}
		
		return arg1;
	}

	private static class ViewHolder {
		TextView deskHintText;
		Button sendNumberButton;
		TextView deskNameText;
		TextView waitDeskNumberText;
		TextView nextNumberText;
	}
}
