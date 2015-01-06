package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/update时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipUpdateOrderStatus {

	/**
	 * 表示订单已结清，作为{@link #AipUpdateOrderStatus(long, int)}的第二个参数传入
	 */
	public static final int TYPE_CLEARED = 1;

	/**
	 * 表示订单已完成，作为{@link #AipUpdateOrderStatus(long, int)}的第二个参数传入
	 */
	public static final int TYPE_COMPLETED = 2;

	long order_id;
	int operation;

	/**
	 * @param order_id
	 *            订单ID
	 * @param operation
	 *            订单更新动作，目前支持如下操作<br/>
	 *            1：更新订单为“已结清”。对于订座订单，表示已补足余款；对于点单订单，表示已完成支付；（有效的外卖订单状态肯定是“已结清”，
	 *            所以对于外卖订单该操作无效）<br/>
	 *            2：更新订单状态为“已完成”。对于订座订单与点单订单，表示已消费，对于外卖订单表示已派送。
	 *            （只有“已结清”的订单才可以进行该操作
	 *            ，如果订单的状态为“未结清”，需要先进行第1个操作，将订单状态改为“已结清”后才能进行该操作）<br/>
	 */
	public AipUpdateOrderStatus(long order_id, int operation) {
		this.order_id = order_id;
		this.operation = operation;
	}

	public long getOrder_id() {
		return order_id;
	}

	public int getOperation() {
		return operation;
	}

}
