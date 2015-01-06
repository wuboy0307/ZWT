package com.koolyun.koolwait.model;

import android.content.Context;
import android.os.AsyncTask;

import com.koolyun.koolwait.MainActivity;
import com.koolyun.koolwait.utils.JsonConverter;
import com.koolyun.koolwait.utils.MyToast;

/**
 * 推送结果处理，抽到推送后调用此任务做相应的处理
 * 
 * @author Edwin
 *
 */
public class PushProcessTask extends AsyncTask<AipPushMessageResponse, Void, AipResponse> {
	@SuppressWarnings("unused")
	private String TAG = PushProcessTask.class.getSimpleName();
	private Context context;
	private OrdersBean ordersBean;

	private AipPushMessageResponse push;

	public PushProcessTask(Context context, OrdersBean ordersBean) {
		this.context = context;
		this.ordersBean = ordersBean;
	}

	@Override
	protected AipResponse doInBackground(AipPushMessageResponse... params) {
		push = params[0];
		int type = push.getType();
		AipResponse response = new AipResponse();

		if (type == AipPushMessageResponse.UPDATE_ORDERS) {
			// 获取订单数目
			response = AipInterface.getOrdersCount(context);
			// Log.i(TAG, response.toString());
			if (!response.isSuccessful()) {
				return response;
			}
			AipOrdersCountResponse aocr = (AipOrdersCountResponse) JsonConverter.fromJson(response.getResult(),
					AipOrdersCountResponse.class);

			// 根据推送的数据更新获取要更新的订单列表
			AipOrdersList aol = new AipOrdersList();
			aol.setType((int) push.getValue());
			response = AipInterface.getOrdersList(context, aol);
			if (!response.isSuccessful()) {
				return response;
			}
			// Log.e(TAG, response.getResult().toString());
			AipOrdersListResponse aolr = (AipOrdersListResponse) JsonConverter.fromJson(response.getResult(),
					AipOrdersListResponse.class);

			// 更新订单数目
			ordersBean.setAipOrdersCountResponse(aocr);
			// 更新订单列表
			ordersBean.setAipOrdersListResponse(aolr, (int) push.getValue());
		} else if (type == AipPushMessageResponse.UPDATE_ORDER_STATUS) {
			// 发送请求，获取订单数目
			response = AipInterface.getOrdersCount(context);
			// Log.i(TAG, response.toString());
			if (!response.isSuccessful()) {
				return response;
			}
			AipOrdersCountResponse aocr = (AipOrdersCountResponse) JsonConverter.fromJson(response.getResult(),
					AipOrdersCountResponse.class);

			// 发送请求，Touch 更新服务器订单状态
			long orderId = push.getValue();
			AipTouchOrderStatus atos = new AipTouchOrderStatus(orderId);
			response = AipInterface.touchOrderStatus(context, atos);
			if (!response.isSuccessful()) {
				return response;
			}
			// Log.e(TAG, response.getResult().toString());
			AipTouchOrderStatusResponse atosr = (AipTouchOrderStatusResponse) JsonConverter.fromJson(
					response.getResult(), AipTouchOrderStatusResponse.class);

			// 更新本地订单数目
			ordersBean.setAipOrdersCountResponse(aocr);
			// 更新本地订单
			ordersBean.updateOrders(atosr.getOrderStatus(), orderId);
		} else if (type == AipPushMessageResponse.UPDATE_QUEUE) {
			// 获取队伍列表
			response = AipInterface.getQueueList(context, ordersBean.getAipSeatCategoriesResponse().toAipQueue());
			if (!response.isSuccessful()) {
				return response;
			}
			AipQueueResponse aqr = (AipQueueResponse) JsonConverter.fromJson(response.getResult(),
					AipQueueResponse.class);
			ordersBean.setAipQueueResponse(aqr);
		}
		return response;
	}

	@Override
	protected void onPostExecute(AipResponse result) {
		super.onPostExecute(result);
		if (result.isSuccessful()) {
			if (push.getType() == AipPushMessageResponse.UPDATE_QUEUE) {
				((MainActivity) context).updateQueueData();
			} else {
				// 来新订单时，播放声音提醒，酷等位不需要处理
//				if (push.getType() == AipPushMessageResponse.UPDATE_ORDERS) {
//					SharePreferenceUtil spu = new SharePreferenceUtil(context, SharePreferenceUtil.FILE_SETTINGS);
//					MainActivity.playSound(spu.getSettingsVolumn() / 100.0f);
//				}
//				// 更新数据
//				((MainActivity) context).updateOrdersData();
			}
		} else {
			if (result.getMsg() != null) {
				MyToast.showShort(context, result.getMsg());
			}
		}
	}
}