package com.koolyun.koolwait.model;

import android.content.Context;
import android.os.AsyncTask;

import com.koolyun.koolwait.utils.JsonConverter;
import com.koolyun.koolwait.utils.MyLog;
import com.koolyun.koolwait.utils.MyToast;
import com.koolyun.koolwait.view.LoadingDialogStyle1;

/**
 * 队列更新任务
 * 
 * @author Edwin
 *
 */
public class UpdateQueueTask extends AsyncTask<AipUpdateQueueStatus, Void, AipResponse> {
	private AipUpdateQueueStatusResponse aipUpdateQueueStatusResponse;
	private AipUpdateQueueStatus aipUpdateQueueStatus;
	private LoadingDialogStyle1 loadingDialog;

	private Context context;

	/**
	 * 队列更新任务
	 * 
	 * @param context
	 * @param queueKind
	 *            队列UI
	 */
	public UpdateQueueTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		loadingDialog = new LoadingDialogStyle1(context, null);
		loadingDialog.show();
		super.onPreExecute();
	}

	@Override
	protected AipResponse doInBackground(AipUpdateQueueStatus... params) {

		aipUpdateQueueStatus = params[0];
		AipResponse response = AipInterface.updateQueueStatus(context, aipUpdateQueueStatus);

		if (!response.isSuccessful()) {
			return response;
		}

		aipUpdateQueueStatusResponse = (AipUpdateQueueStatusResponse) JsonConverter.fromJson(response.getResult(),
				AipUpdateQueueStatusResponse.class);
		return response;
	}

	@Override
	protected void onPostExecute(AipResponse result) {
		super.onPostExecute(result);

		if (result.isSuccessful()) {
			AipUpdateQueueStatusResponse.Queue queue = aipUpdateQueueStatusResponse.getQueue();

			if (aipUpdateQueueStatus.getOperation() == AipUpdateQueueStatus.CREATE_QUEUE) {
				// TODO 创建队伍
				
			} else if (aipUpdateQueueStatus.getOperation() == AipUpdateQueueStatus.STOP_QUEUE) {
				// TODO 停止队伍
			} else { // TODO 队伍减一
				if (queue == null) {
					
				} else {
					
				}
			}

		} else {
			if (result.getMsg() != null) {
				MyLog.i(result.getMsg());
				MyToast.showShort(context, result.getMsg());
			}
		}

		loadingDialog.dismiss();
	}
}