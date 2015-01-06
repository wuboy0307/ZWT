package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/order/info时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipOrderDetails {

	/**
	 * 订单编号，作为{@link #AipOrderDetails(int, String)}的第一个参数输入
	 */
	public static final int TYPE_ORDERS = 1;

	/**
	 * 消费验证，作为{@link #AipOrderDetails(int, String)}的第一个参数输入
	 */
	public static final int TYPE_SALE_CAPTCHA = 2;

	int key_type;
	String key_value;

	/**
	 * @param key_type
	 *            关键字类型（1：订单编号，2：消费验证码）
	 * @param key_value
	 *            关键字（订单编号或消费验证码，对应key_type的值）
	 */
	public AipOrderDetails(int key_type, String key_value) {
		this.key_type = key_type;
		this.key_value = key_value;
	}

}
