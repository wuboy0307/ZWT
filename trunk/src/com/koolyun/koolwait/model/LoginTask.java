package com.koolyun.koolwait.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.koolyun.koolwait.LoginActivity;
import com.koolyun.koolwait.MainActivity;
import com.koolyun.koolwait.R;
import com.koolyun.koolwait.utils.Digest;
import com.koolyun.koolwait.utils.JsonConverter;
import com.koolyun.koolwait.utils.MyLog;
import com.koolyun.koolwait.utils.MyToast;
import com.koolyun.koolwait.view.LoadingDialogStyle1;

/**
 * 登入任务
 * 
 * @author Edwin
 *
 */
public class LoginTask extends AsyncTask<Void, String, AipResponse> {
	private String TAG = LogoutTask.class.getSimpleName();
	private AipLoginResponse alr = null;
	private AipOrdersCountResponse aocr = null;
	private AipOrdersListResponse[] aolrs = new AipOrdersListResponse[3];
	private AipSeatCategoriesResponse ascr = null;
	private AipQueueResponse aqr = null;
	private LoadingDialogStyle1 loadingDialog;

	private Context context;
	private String username;
	private String password;
	// private String userId;
	private View clickedButton;

	/**
	 * @param context
	 * @param username
	 *            用户名
	 * @param password
	 *            密码的MD5值
	 * @param userId
	 *            百度云推送user id
	 * @param clickedButton
	 *            所点击的按键
	 */
	public LoginTask(Context context, String username, String password, String userId, View clickedButton) {
		this.context = context;
		this.username = username;
		this.password = password;
		// this.userId = userId;
		this.clickedButton = clickedButton;
	}

	/**
	 * 初始化推送服务
	 */
	private void initPushService() {
		SharePreferenceUtil spu = new SharePreferenceUtil(context, SharePreferenceUtil.FILE_SETTINGS);
		if (!spu.getSettingsBaiduPushBind()) {
			PushManager.startWork(context.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
					PushUtils.getMetaValue(context, "api_key"));
		} else {
			LoginActivity.userId = spu.getSettingsBaiduPushUserId();
			Log.i(TAG, "getDefault User id " + LoginActivity.userId);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		clickedButton.setEnabled(false);
		loadingDialog = new LoadingDialogStyle1(context, " 0%");
		loadingDialog.show();
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		loadingDialog.setMsg(values[0]);
	}

	@Override
	protected AipResponse doInBackground(Void... arg0) {
		if (!NetworkUtil.isNetWorkAvilable(context)) {
			return new AipResponse(AipResponse.CODE_NETWORK_NOT_AVAILABLE, context.getResources().getString(
					R.string.network_not_available));
		}
		initPushService();

		// 等待推送绑定完成
		while (LoginActivity.userId == null) {
			Thread.yield();
		}
		MyLog.e("百度验证通过。。。。。。");

		// 登陆，获取登陆信息
		AipResponse response = AipInterface.Login(context, username, Digest.md5Sum(password), LoginActivity.userId);
		// Log.i(TAG, response.toString());
		if (!response.isSuccessful()) {
			return response;
		}
		alr = (AipLoginResponse) JsonConverter.fromJson(response.getResult(), AipLoginResponse.class);
		publishProgress("20%");

		// 获取订单数目
		response = AipInterface.getOrdersCount(context);
		// Log.i(TAG, response.toString());
		if (!response.isSuccessful()) {
			return response;
		}
		aocr = (AipOrdersCountResponse) JsonConverter.fromJson(response.getResult(), AipOrdersCountResponse.class);
		publishProgress("40%");

		// 获取订单列表
		for (int i = 0; i < 3; i++) {
			AipOrdersList aol = new AipOrdersList();
			aol.setType(i + 1);
			response = AipInterface.getOrdersList(context, aol);
			if (!response.isSuccessful()) {
				return response;
			}
			Log.e(TAG, response.getResult().toString());
			aolrs[i] = (AipOrdersListResponse) JsonConverter
					.fromJson(response.getResult(), AipOrdersListResponse.class);
			// publishProgress("60%");
		}

		// 获取餐位类型
		response = AipInterface.getSeatList(context);
		if (!response.isSuccessful()) {
			return response;
		}
		MyLog.e("seat type:"+response.getResult().toString());
		ascr = (AipSeatCategoriesResponse) JsonConverter
				.fromJson(response.getResult(), AipSeatCategoriesResponse.class);
		Log.i(TAG, ascr.toString());
		publishProgress("80%");

		// 获取队伍列表
		response = AipInterface.getQueueList(context, ascr.toAipQueue());
		if (!response.isSuccessful()) {
			return response;
		}
		aqr = (AipQueueResponse) JsonConverter.fromJson(response.getResult(), AipQueueResponse.class);

		publishProgress("100%");
		if (response.isSuccessful()) {

			// 设置订餐易数据
			MainActivity.setOrdersBean(new OrdersBean(alr, aocr, aolrs, ascr, aqr));

			// 进入主界面
			Intent intent = new Intent();
			intent.setClass(context, MainActivity.class);
			intent.putExtra("userId", LoginActivity.userId);
			context.startActivity(intent);
			((Activity) context).finish();
		}

		return response;
	}

	@Override
	protected void onPostExecute(AipResponse result) {
		super.onPostExecute(result);

		if (!result.isSuccessful()) {

			if (result.getMsg() != null) {
				MyToast.showShort(context, result.getMsg());
			}
		}
		clickedButton.setEnabled(true);
		loadingDialog.dismiss();
	}
}