package com.koolyun.koolwait.model;

/**
 * 调用接口/easyfood-mposapi/queue/number时POST给服务器的数据
 * 
 * @author Edwin
 */
public class AipTakeNumber {

	/**
	 * 餐位类型ID
	 */
	long seat_category_id;

	/**
	 * 联系人电话
	 */
	String contact_tel;

	public AipTakeNumber(long seat_category_id, String contact_tel) {
		this.seat_category_id = seat_category_id;
		this.contact_tel = contact_tel;
	}

}
