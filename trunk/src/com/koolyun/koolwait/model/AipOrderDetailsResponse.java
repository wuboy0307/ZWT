package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/info时从服务器返回的数据
 * 
 * @author Edwin
 *
 */
public class AipOrderDetailsResponse {

	private Order order;

	/**
	 * 获取订单详情
	 * 
	 * @return {@link }
	 */
	public Order getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return "AipOrderDetailsResponse [order=" + order + "]";
	}

}
