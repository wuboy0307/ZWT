package com.koolyun.koolwait.model;

import java.util.Arrays;

/**
 * 调用接口/easyfood-mposapi/queue/list时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipQueueResponse {

	/**
	 * 队伍列表（列表元素个数可能小于请求中指定的餐位类型个数，表示有的餐位类型下没有队伍，或者请求的餐位类型不存在）
	 */
	Queue[] queues;

	public Queue[] getQueues() {
		return queues;
	}

	public void updateQueues(Queue[] newQueues) {
		for (int i = 0; i < queues.length; i++) {
			for (int j = 0; j < newQueues.length; j++) {
				if (queues[i].getSeat_category_id() == newQueues[j].getSeat_category_id()) {
					queues[i] = newQueues[j];
					break;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "AipQueueResponse [queues=" + Arrays.toString(queues) + "]";
	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel out, int flags) {
	// out.writeInt(called_number);
	// out.writeInt(delivered_number);
	// out.writeLong(seat_category_id);
	// }
	//
	// public static final Parcelable.Creator<AipQueueResponse> CREATOR = new
	// Parcelable.Creator<AipQueueResponse>() {
	//
	// @Override
	// public AipQueueResponse createFromParcel(Parcel in) {
	// AipQueueResponse r = new AipQueueResponse();
	// r.called_number = in.readInt();
	// r.delivered_number = in.readInt();
	// r.seat_category_id = in.readLong();
	// return r;
	// }
	//
	// @Override
	// public AipQueueResponse[] newArray(int size) {
	// return new AipQueueResponse[size];
	// }
	// };
}
