package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/queue/list时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipQueue {
	/**
	 * 指定餐位类型的ID列表
	 */
	long[] seat_categories;

	public AipQueue() {

	}

	public AipQueue(long[] categories) {
		seat_categories = categories;
	}
}
