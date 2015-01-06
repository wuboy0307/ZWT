package com.koolyun.koolwait.model;

public class AipPushMessageResponse {

	/**
	 * 该消息表示有新订单。新订单类型根据value的值。客户端需要重新获取订单数量，以及相应订单列表。（调用/order/count 和
	 * /order/list接口）
	 */
	public static final int UPDATE_ORDERS = 1;

	/**
	 * 该消息表示已有订单状态发生了改变。改变订单的订单ID根据value的值。客户端需要重新获取订单数量，以及刷新相应订单的状态。（调用/order/
	 * count 和 /order/touch接口）
	 */
	public static final int UPDATE_ORDER_STATUS = 2;

	/**
	 * 该消息表示某餐位的队伍发生了变化。改变餐位类型的ID根据value的值。客户端需要刷新相应餐位的队伍状态。（调用/queue/list接口）
	 */
	public static final int UPDATE_QUEUE = 3;

	int type;
	long value;
	String caused_by;

	@Override
	public String toString() {
		return "AipPushMessageResponse [type=" + type + ", value=" + value + ", caused_by=" + caused_by + "]";
	}

	public int getType() {
		return type;
	}

	public long getValue() {
		return value;
	}

	public String getCaused_by() {
		return caused_by;
	}

}
