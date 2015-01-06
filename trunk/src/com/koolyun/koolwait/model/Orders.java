package com.koolyun.koolwait.model;

import android.content.Context;

import com.koolyun.koolwait.R;

public class Orders {

	/**
	 * 预订
	 */
	public static final int REVERSATION_TYPE = 1;

	/**
	 * 点单
	 */
	public static final int LOCAL_TYPE = 2;

	/**
	 * 外送
	 */
	public static final int TAKEOUT_TYPE = 3;

	/**
	 * 未完成状态
	 */
	public static final int UNCOMPLETED_STATUS = 1;

	/**
	 * 已完成状态
	 */
	public static final int COMPLETED_STATUS = 2;

	/**
	 * 未过时
	 */
	public static final int UNEXPIRED_STATUS = 1;

	/**
	 * 已过时
	 */
	public static final int EXPIRED_STATUS = 2;

	/** 已消费 */
	public static final int SALED_STATUS = 2;

	/**
	 * 未消费
	 */
	public static final int UNSALED_STATUS = 1;

	/**
	 * 定金支付
	 */
	public static final int BARGAIN_PAY_STATUS = 1;

	/**
	 * 全额支付
	 */
	public static final int ALL_PAY_STATUS = 2;

	/**
	 * 已支付
	 */
	public static final int PAID_STATUS = 2;

	/**
	 * 未支付
	 */
	public static final int UNPAID_STATUS = 1;

	/**
	 * 已结清
	 */
	public static final int CLEARED_STATUS = 2;

	/**
	 * 未结清
	 */
	public static final int UNCLEARED_STATUS = 1;

	/**
	 * 已派送
	 */
	public static final int DELIVERED_STATUS = 2;

	/**
	 * 未派送
	 */
	public static final int UNDELIVERED_STATUS = 1;

	/**
	 * 未知状态
	 */
	public static final int UNKNOWN_STATUS = -1;

	private long id;
	private String order_no;
	private int type;
	private String dinner_time;
	private String seat;
	private int pay_method;
	private int clear;
	private int completed;
	private int expired;
	private String contact_person;
	private String contact_tel;

	@Override
	public String toString() {
		return "Orders [id=" + id + ", order_no=" + order_no + ", type=" + type + ", dinner_time=" + dinner_time
				+ ", seat=" + seat + ", pay_method=" + pay_method + ", clear=" + clear + ", completed=" + completed
				+ ", expired=" + expired + ", contact_person=" + contact_person + ", contact_tel=" + contact_tel + "]";
	}

	public long getId() {
		return id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public int getType() {
		return type;
	}

	public String getDinner_time() {
		return dinner_time;
	}

	public String getSeat() {
		return seat;
	}

	public int getPay_method() {
		return pay_method;
	}

	public int getClear() {
		return clear;
	}

	public int getCompleted() {
		return completed;
	}

	public int getExpired() {
		return expired;
	}

	public String getContact_person() {
		return contact_person;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setDinner_time(String dinner_time) {
		this.dinner_time = dinner_time;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public void setPay_method(int pay_method) {
		this.pay_method = pay_method;
	}

	public void setClear(int clear) {
		this.clear = clear;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public void updateOrder(OrderStatus order) {
		this.clear = order.getClear();
		this.completed = order.getCompleted();
		this.expired = order.getExpired();
		// this.order.getPaid_amount();
	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel out, int flags) {
	// out.writeLong(id);
	// out.writeString(order_no);
	// out.writeInt(type);
	// out.writeString(dinner_time);
	// out.writeString(seat);
	// out.writeInt(pay_method);
	// out.writeInt(clear);
	// out.writeInt(completed);
	// out.writeInt(expired);
	// out.writeString(contact_person);
	// out.writeString(contact_tel);
	// }

	public String[] toStringArray(Context context) {
		String[] array = new String[5];
		if (type == REVERSATION_TYPE) {
			array[0] = order_no;
			array[1] = dinner_time;
			array[2] = seat;
			array[3] = getPayStatusString(context);
			array[4] = getCompleteStatusString(context);
		} else if (type == LOCAL_TYPE) {
			array[0] = order_no;
			array[1] = dinner_time;
			array[2] = seat;
			array[3] = getClearStatusString(context);
			array[4] = getCompleteStatusString(context);
		} else {
			array[0] = order_no;
			array[1] = dinner_time;
			array[2] = contact_person;
			array[3] = contact_tel;
			array[4] = getDeliveredStatusString(context);
		}

		return array;
	}

	public boolean isCompleted() {
		return completed == COMPLETED_STATUS ? true : false;
	}

	public boolean isExpired() {
		return expired == EXPIRED_STATUS ? true : false;
	}

	public int getPayStatus() {
		return pay_method;
	}

	public int getClearStatus() {
		return clear;
	}

	private String getPayStatusString(Context context) {
		// if (pay_method == ALL_PAY_STATUS)
		// return context.getResources().getString(R.string.all_pay);
		// else if (pay_method == BARGAIN_PAY_STATUS)
		// return context.getResources().getString(R.string.bargain_pay);

		if (this.completed == UNCOMPLETED_STATUS) {
			return context.getResources().getString(R.string.un_arrive);
		} else if (this.completed == COMPLETED_STATUS) {
			return context.getResources().getString(R.string.already_arrive);
		}
		return context.getResources().getString(R.string.unknown_status);
	}

	private String getClearStatusString(Context context) {
		if (completed == UNCOMPLETED_STATUS)
			return context.getResources().getString(R.string.un_paid);
		else if (completed == COMPLETED_STATUS)
			return context.getResources().getString(R.string.already_paid);

		return context.getResources().getString(R.string.unknown_status);
	}

	private String getCompleteStatusString(Context context) {
		if (completed == COMPLETED_STATUS)
			return context.getResources().getString(R.string.delivered);
		else if (completed == UNCOMPLETED_STATUS)
			return context.getResources().getString(R.string.undelivered);

		return context.getResources().getString(R.string.unknown_status);
	}

	private String getDeliveredStatusString(Context context) {
		if (completed == COMPLETED_STATUS)
			return context.getResources().getString(R.string.delivered);
		else if (completed == UNCOMPLETED_STATUS)
			return context.getResources().getString(R.string.undelivered);

		return context.getResources().getString(R.string.unknown_status);
	}
}