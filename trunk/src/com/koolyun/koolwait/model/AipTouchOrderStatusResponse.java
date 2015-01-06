package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/touch时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipTouchOrderStatusResponse {

	OrderStatus order;

	public OrderStatus getOrderStatus() {
		return order;
	}

}
