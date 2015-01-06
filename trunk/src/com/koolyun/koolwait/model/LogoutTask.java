package com.koolyun.koolwait.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.koolyun.koolwait.LoginActivity;
import com.koolyun.koolwait.utils.MyToast;
import com.koolyun.koolwait.view.LoadingDialogStyle1;

/**
 * 登出任务
 * 
 * @author Edwin
 *
 */
public class LogoutTask extends AsyncTask<Context, Void, AipResponse> {
	@SuppressWarnings("unused")
	private String TAG = LogoutTask.class.getSimpleName();
	private Context context;
	private LoadingDialogStyle1 loadingDialog;

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		loadingDialog = new LoadingDialogStyle1(context, null);
		loadingDialog.show();
	}

	@Override
	protected AipResponse doInBackground(Context... params) {
		context = params[0];
		publishProgress();

		AipResponse response = AipInterface.Logout(context);
		// Log.i(TAG, response.toString());

		return response;
	}

	@Override
	protected void onPostExecute(AipResponse result) {
		super.onPostExecute(result);

		if (loadingDialog != null)
			loadingDialog.dismiss();

		// 回到登陆界面
		context.startActivity(new Intent(context, LoginActivity.class));
		((Activity) context).finish();

		if (!result.isSuccessful()) {
			if (result.getMsg() != null) {
				MyToast.showShort(context, result.getMsg());
			}
		}

	}
}