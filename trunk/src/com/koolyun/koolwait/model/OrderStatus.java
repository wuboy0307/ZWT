package com.koolyun.koolwait.model;

/**
 * 订单状态
 * 
 * @author Edwin
 *
 */
public class OrderStatus {
	/**
	 * 订单结算状态（1：未结清，2：已结清）。对于订座订单，“未结清”表示订单为定金支付且尚未补足余款，“已结清”表示订单为全额支付，
	 * 或者为定金支付且已补足余款；对于点单订单，“未结清”表示未付款，“已结清”表示已付款。外卖订单必须结清，所以该字段固定为2。
	 */
	int clear;

	/**
	 * 订单完成状态（1：未完成，2：已完成）。对于订座订单与点单订单，“未完成”表示未消费，“已完成”表示已消费；对于外卖的订单，“未完成”表示未派送，
	 * “已完成”表示已派送。
	 */
	int completed;

	/**
	 * 订单是否超时（1：未超时，2：已超时）。该字段只对订座订单有意义，表示已经过了订单预订时间。点单订单与外卖订单该字段固定为1。
	 */
	int expired;

	/**
	 * 订单已支付金额
	 */
	double paid_amount;

	@Override
	public String toString() {
		return "OrderStatus [clear=" + clear + ", completed=" + completed + ", expired=" + expired + ", paid_amount="
				+ paid_amount + "]";
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

	public double getPaid_amount() {
		return paid_amount;
	}
}
