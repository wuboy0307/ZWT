package com.koolyun.koolwait.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 调用接口/easyfood-mposapi/auth/login时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipLoginResponse implements Parcelable {

	private String access_token;
	private int expires_in;
	private String brand;
	private String restaurant;
	private String user;
	private int support_queue;
	private int support_take_out;

	@Override
	public String toString() {
		return "AipLoginResponse [access_token=" + access_token + ", expires_in=" + expires_in + ", brand=" + brand
				+ ", restaurant=" + restaurant + ", user=" + user + ", support_queue=" + support_queue + "]";
	}

	protected AipLoginResponse(Parcel in) {
		access_token = in.readString();
		expires_in = in.readInt();
		brand = in.readString();
		restaurant = in.readString();
		user = in.readString();
	}

	public String getAccess_token() {
		return access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public String getBrand() {
		return brand;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public String getUser() {
		return user;
	}

	public int getSupport_queue() {
		return support_queue;
	}

	public int getSupport_take_out() {
		return support_take_out;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(access_token);
		out.writeInt(expires_in);
		out.writeString(brand);
		out.writeString(restaurant);
		out.writeString(user);
	}

	public static final Parcelable.Creator<AipLoginResponse> CREATOR = new Parcelable.Creator<AipLoginResponse>() {

		@Override
		public AipLoginResponse createFromParcel(Parcel in) {
			return new AipLoginResponse(in);
		}

		@Override
		public AipLoginResponse[] newArray(int size) {
			return new AipLoginResponse[size];
		}
	};
}
