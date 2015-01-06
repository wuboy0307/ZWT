package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/list时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipOrdersList {

	int type;
	long since_id;
	int completed;
	int pay_method;
	int clear;

	/**
	 * 设置目标订单列表的订单类型
	 * 
	 * @param type
	 *            1：订座，2：点单，3：外卖
	 */
	public void setType(int type) {
		this.type = type;
	}

}
