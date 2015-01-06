package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/update时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipUpdateOrderStatusResponse {

	OrderStatus order;

	@Override
	public String toString() {
		return "AipUpdateOrderStatusResponse [order=" + order + "]";
	}

	public OrderStatus getOrderStatus() {
		return order;
	}

}
