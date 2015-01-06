package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/touch时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipTouchOrderStatus {
	/**
	 * 订单ID
	 */
	long order_id;

	public AipTouchOrderStatus(long order_id) {
		this.order_id = order_id;
	}

}
