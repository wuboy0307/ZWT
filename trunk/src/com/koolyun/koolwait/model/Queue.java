package com.koolyun.koolwait.model;

/**
 * 队伍列表（列表元素个数可能小于请求中指定的餐位类型个数，表示有的餐位类型下没有队伍，或者请求的餐位类型不存在）
 * 
 * @author Edwin
 *
 */
/**
 * @author Feel feel.li@koolpos.com
 *
 */
public class Queue {
	/**
	 * 该队伍已叫到的号码
	 */
	int called_number;

	/**
	 * 该队伍已发放到的号码
	 */
	int delivered_number;

	/**
	 * 该队伍所属餐位类型ID
	 */
	long seat_category_id;
	
	
	/**
	 * 该队伍的状态（1：排队中；2：已暂停（暂停的队伍只能叫号不能发号）
	 */
	int status;

	public Queue() {

	}

	@Override
	public String toString() {
		return "Queue [called_number=" + called_number + ", delivered_number=" + delivered_number
				+ ", seat_category_id=" + seat_category_id + "]";
	}

	public int getCalled_number() {
		return called_number;
	}

	public int getDelivered_number() {
		return delivered_number;
	}

	public long getSeat_category_id() {
		return seat_category_id;
	}

	public void setCalled_number(int called_number) {
		this.called_number = called_number;
	}

	public void setDelivered_number(int delivered_number) {
		this.delivered_number = delivered_number;
	}

	public void setSeat_category_id(long seat_category_id) {
		this.seat_category_id = seat_category_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}