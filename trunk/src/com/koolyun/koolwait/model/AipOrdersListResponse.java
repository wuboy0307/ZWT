package com.koolyun.koolwait.model;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 调用接口/easyfood-mposapi/order/list时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipOrdersListResponse implements Parcelable {

	private Orders[] orders;

	protected AipOrdersListResponse(Parcel source) {
		orders = (Orders[]) source.readParcelableArray(Orders.class.getClassLoader());
	}

	@Override
	public String toString() {
		return "AipOrdersListResponse [orders=" + Arrays.toString(orders) + "]";
	}

	/**
	 * 获取订单列表
	 * 
	 * @return 订单列表
	 */
	public Orders[] getOrders() {
		return orders;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// out.writeParcelableArray(orders, 0);
	}

	public static final Parcelable.Creator<AipOrdersListResponse> CREATOR = new Parcelable.Creator<AipOrdersListResponse>() {

		@Override
		public AipOrdersListResponse createFromParcel(Parcel in) {
			return new AipOrdersListResponse(in);
		}

		@Override
		public AipOrdersListResponse[] newArray(int size) {
			return new AipOrdersListResponse[size];
		}
	};

}
